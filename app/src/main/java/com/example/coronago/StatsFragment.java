package com.example.coronago;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private static final String URL_DATA = "https://api.covid19india.org/v2/state_district_wise.json";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RequestQueue mq;

    String val;

    ArrayList<String> stateList;
    Spinner State;


    List<ListItem> listItems;

    public void init(){
        recyclerView = getView().findViewById(R.id.recyclerView);
        mq = Volley.newRequestQueue(getActivity());
        State = getView().findViewById(R.id.State);
        stateList = new ArrayList<>();
        stateList.add("Choose State!");
        stateList.add("Andaman and Nicobar Islands");
        stateList.add("Andhra Pradesh");
        stateList.add("Arunachal Pradesh");
        stateList.add("Assam");
        stateList.add("Bihar");
        stateList.add("Chandigarh");
        stateList.add("Chhattisgarh");
        stateList.add("Dadra and Nagar Haveli and Daman and Diu");
        stateList.add("Delhi");
        stateList.add("Goa");
        stateList.add("Gujarat");
        stateList.add("Haryana");
        stateList.add("Himachal Pradesh");
        stateList.add("Jammu and Kashmir");
        stateList.add("Jharkhand");
        stateList.add("Karnataka");
        stateList.add("Kerala");
        stateList.add("Ladakh");
        stateList.add("Lakshadweep");
        stateList.add("Madhya Pradesh");
        stateList.add("Maharashtra");
        stateList.add("Manipur");
        stateList.add("Meghalaya");
        stateList.add("Mizoram");
        stateList.add("Nagaland");
        stateList.add("Odisha");
        stateList.add("Puducherry");
        stateList.add("Punjab");
        stateList.add("Rajasthan");
        stateList.add("Sikkim");
        stateList.add("Tamil Nadu");
        stateList.add("Telangana");
        stateList.add("Tripura");
        stateList.add("Uttar Pradesh");
        stateList.add("Uttarakhand");
        stateList.add("West Bengal");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        State.setAdapter(stateAdapter);

        State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose State!")){
                    StyleableToast.makeText(parent.getContext(),"Please choose state!",R.style.exampleToast).show();
                }else{
                    String state = parent.getItemAtPosition(position).toString();
                    val = state.toLowerCase();
                    clearRecyclerViewData();
                    loadRecyclerViewData(val);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadRecyclerViewData(final String val) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data...");
        progressDialog.show();



        JsonArrayRequest request = new JsonArrayRequest(URL_DATA, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();


                try {

                    //JSONArray jsonArray = response.getJSONArray("statewise");
                    int str_len = response.length();
                    String lenn = Integer.toString(str_len);
                    Log.d("lenn",lenn);
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject st = response.getJSONObject(i);

                        String statename = st.getString("state");
                        statename = statename.toLowerCase();
                        if(statename.equals(val))
                        {
                            JSONArray jsonArray = st.getJSONArray("districtData");
                            for(int j=0;j<jsonArray.length();j++)
                            {
                                JSONObject dis = jsonArray.getJSONObject(j);
                                ListItem item = new ListItem(val,
                                        dis.getString("district"),
                                        dis.getString("active"),
                                        dis.getString("confirmed"),
                                        dis.getString("deceased"),
                                        dis.getString("recovered"));
                                listItems.add(item);
                                String districtname = dis.getString("district");
                                String active = dis.getString("active");
                                String confirmed = dis.getString("confirmed");
                                String deaths = dis.getString("deceased");
                                String recovered = dis.getString("recovered");
                                districtname = "District : "+ districtname;
                                active = "Active Cases : " + active;
                                confirmed = "Confirmed Cases : " + confirmed;
                                deaths = "Total Deaths : " + deaths;
                                recovered = "Total Recovered : " + recovered;



                            }
                            adapter = new Adapter(listItems, getContext());
                            recyclerView.setAdapter(adapter);

                        }


                    }
                }
                catch (JSONException e) {
                    StyleableToast.makeText(getContext(),"Error "+e.getMessage(),R.style.exampleToast).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                StyleableToast.makeText(getContext(), error.getMessage(),R.style.exampleToast).show();

            }
        });
        mq.add(request);
    }

    private void clearRecyclerViewData(){
        listItems.clear();
    }
}
