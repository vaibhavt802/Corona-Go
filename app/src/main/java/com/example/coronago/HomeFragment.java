package com.example.coronago;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import com.muddzdev.styleabletoast.StyleableToast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment{


    public HomeFragment(){

    }

    Button trackClick;
    TextView tvCases, tvTodayCases, tvDeaths, tvTodayDeaths, tvRecovered, tvTodayRecovered, tvActive;
    TextView tvCritical, tvCasesPerOneMillion, tvDeathsPerOneMillion, tvTests, tvTestsPerOneMillion;
    SimpleArcLoader mSimpleArcLoader;
    ScrollView mScrollView;
    PieChart mPieChart;
    boolean FLAG=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);




        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    trackClick.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navigate_to_activity();
        }
    });

    }

    private void navigate_to_activity() {
            Intent intent = new Intent(getActivity(),AffectedStates.class);
            startActivity(intent);
    }

    private void init() {
        trackClick = getActivity().findViewById(R.id.btntrack);
        tvCases = getActivity().findViewById(R.id.tvcases);
        tvTodayCases = getActivity().findViewById(R.id.tvtodaycases);
        tvDeaths =getActivity().findViewById(R.id.tvdeaths);
        tvTodayDeaths = getActivity().findViewById(R.id.tvtodaydeaths);
        tvRecovered = getActivity().findViewById(R.id.tvrecovered);
        tvTodayRecovered = getActivity().findViewById(R.id.tvtodayrecovered);
        tvActive = getActivity().findViewById(R.id.tvactive);
        tvCritical = getActivity().findViewById(R.id.tvcritical);
        tvCasesPerOneMillion = getActivity().findViewById(R.id.tvcasesperonemillion);
        tvDeathsPerOneMillion = getActivity().findViewById(R.id.tvdeathsperonemillion);
        tvTests = getActivity().findViewById(R.id.tvtests);
        tvTestsPerOneMillion = getActivity().findViewById(R.id.tvtestsperonemillion);
        mSimpleArcLoader = getActivity().findViewById(R.id.loader);
        mScrollView = getActivity().findViewById(R.id.scrollStats);
        mPieChart = getActivity().findViewById(R.id.piechart);

        fetchData ();
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/countries/india?yesterday=true&strict=true&query";

        mSimpleArcLoader.start();

        StringRequest mRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject mJsonObject = new JSONObject(response.toString());

                            tvCases.setText(mJsonObject.getString("cases"));
                            tvTodayCases.setText(mJsonObject.getString("todayCases"));
                            tvDeaths.setText(mJsonObject.getString("deaths"));
                            tvTodayDeaths.setText(mJsonObject.getString("todayDeaths"));
                            tvRecovered.setText(mJsonObject.getString("recovered"));
                            tvTodayRecovered.setText(mJsonObject.getString("todayRecovered"));
                            tvActive.setText(mJsonObject.getString("active"));
                            tvCritical.setText(mJsonObject.getString("critical"));
                            tvCasesPerOneMillion.setText(mJsonObject.getString("casesPerOneMillion"));
                            tvDeathsPerOneMillion.setText(mJsonObject.getString("deathsPerOneMillion"));
                            tvTests.setText(mJsonObject.getString("tests"));
                            tvTestsPerOneMillion.setText(mJsonObject.getString("testsPerOneMillion"));
                            
                            
                            getOnce();

//                            mPieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
//                            mPieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
//                            mPieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvDeaths.getText().toString()), Color.parseColor("#EF5350")));
//                            mPieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));

                            mPieChart.startAnimation();
                            mSimpleArcLoader.stop();
                            mSimpleArcLoader.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mSimpleArcLoader.stop();
                            mSimpleArcLoader.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSimpleArcLoader.stop();
                mSimpleArcLoader.setVisibility(View.GONE);
                mScrollView.setVisibility(View.VISIBLE);
                StyleableToast.makeText(getActivity(), error.getMessage(), R.style.exampleToast).show();
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(mRequest);

    }

    private void getOnce() {
        if(FLAG==false){
            mPieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
            mPieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
            mPieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvDeaths.getText().toString()), Color.parseColor("#EF5350")));
            mPieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
            FLAG = true;
        }else{

        }
    }
}
