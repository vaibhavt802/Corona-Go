package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedStates extends AppCompatActivity {

    private EditText mEdtSearch;
    private ListView mListView;
    private SimpleArcLoader mSimpleArcLoader;

    public static List<StatesModel> statesModelList = new ArrayList<>();
    StatesModel statesModel;
    MyCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_states);

        mEdtSearch = findViewById(R.id.edtsearch);
        mListView = findViewById(R.id.listview);
        mSimpleArcLoader = findViewById(R.id.loader);

        getSupportActionBar().setTitle("Affected States");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position",position));
            }
        });

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url = "https://covid-india-cases.herokuapp.com/states/";

        mSimpleArcLoader.start();

        StringRequest mRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String stateName = jsonObject.getString("state");
                                String cases = jsonObject.getString("noOfCases");
                                String deaths = jsonObject.getString("deaths");
                                String cured = jsonObject.getString("cured");

                                statesModel = new StatesModel(stateName,cured,deaths,cases);
                                statesModelList.add(statesModel);
                            }
                            myCustomAdapter = new MyCustomAdapter(AffectedStates.this,statesModelList);
                            mListView.setAdapter(myCustomAdapter);
                            mSimpleArcLoader.stop();
                            mSimpleArcLoader.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mSimpleArcLoader.stop();
                            mSimpleArcLoader.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StyleableToast.makeText(AffectedStates.this, error.getMessage(), R.style.exampleToast).show();
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(mRequest);

    }
}
