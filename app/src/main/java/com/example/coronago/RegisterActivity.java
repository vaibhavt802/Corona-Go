package com.example.coronago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnLogout, btnLocation, btnSubmit;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private EditText edtName, edtMobile, edtNoOfSuspect, edtLocation;
    String userID;
    private ProgressDialog progressDialog;

    private static final String TAG="ReportFragement";
    int LOCATION_REQUEST_CODE=10001;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);

        btnLogout = findViewById(R.id.btnlogout);
        btnLocation = findViewById(R.id.btnlocation);
        edtName = findViewById(R.id.edtName);
        edtMobile = findViewById(R.id.edtmobile);
        edtNoOfSuspect = findViewById(R.id.edtsuspect);
        edtLocation = findViewById(R.id.edtlocation);
        btnSubmit = findViewById(R.id.btnsubmit);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                        .PERMISSION_GRANTED){
                    getLastLocation();
                }else
                {
                    askLocationPermission();
                }
            }
        });

        //submit data to firebase
        btnSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                showDialog();
                String name = edtName.getText().toString().trim();
                String mobile = edtMobile.getText().toString().trim();
                String suspect = edtNoOfSuspect.getText().toString().trim();
                String location = edtLocation.getText().toString().trim();

                if (name.isEmpty()) {
                    destroyDialog();
                    edtName.setError("Enter name");
                } else if (mobile.isEmpty() || mobile.length() < 10 || mobile.length() > 10) {
                    destroyDialog();
                    edtMobile.setError("Enter proper mobile number");
                } else if (suspect.isEmpty()) {
                    destroyDialog();
                    edtNoOfSuspect.setError("Enter suspect");
                }
                else if (location.isEmpty()){
                    destroyDialog();
                    edtLocation.setError("Please press Autofill Location");

                }
                else {
                    DocumentReference documentReference = firestore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("mobile number", mobile);
                    user.put("No. of Suspect", suspect);
                    user.put("Location(Lat and Long)",location);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            destroyDialog();
                            StyleableToast.makeText(RegisterActivity.this, "Reported successfully", R.style.exampleToast).show();
                        }
                    });
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                StyleableToast.makeText(RegisterActivity.this, "Thank You!", R.style.exampleToast).show();
                Intent i = new Intent(RegisterActivity.this,navigation_activity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void getLastLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(RegisterActivity.this);

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    //we have a location
                    edtLocation.setText(""+location.getLatitude()+"\n"+location.getLongitude());
                }
                else
                {
                    edtLocation.setText("location was null");
                }

            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StyleableToast.makeText(RegisterActivity.this, "onFailure "+e.getMessage(), R.style.exampleToast).show();
            }
        });
    }

    private void askLocationPermission(){
        if(ContextCompat.checkSelfPermission(RegisterActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permission granted
                getLastLocation();
            }else{
                //permission not granted
                StyleableToast.makeText(this, "Permission denied!", R.style.exampleToast).show();
            }
        }
    }

    public void showDialog(){
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Uploading to database...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void destroyDialog(){
        progressDialog.dismiss();
    }
}
