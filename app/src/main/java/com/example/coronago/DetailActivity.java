package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int positionState;
    TextView tvState, tvCases, tvDeaths, tvRecovered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionState = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+AffectedStates.statesModelList.get(positionState).getState());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvState = findViewById(R.id.tvstate);
        tvCases = findViewById(R.id.tvcases);
        tvDeaths = findViewById(R.id.tvdeaths);
        tvRecovered = findViewById(R.id.tvrecovered);

        tvState.setText(AffectedStates.statesModelList.get(positionState).getState());
        tvCases.setText(AffectedStates.statesModelList.get(positionState).getNoOfCases());
        tvDeaths.setText(AffectedStates.statesModelList.get(positionState).getDeaths());
        tvRecovered.setText(AffectedStates.statesModelList.get(positionState).getCured());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

