package com.example.amit.parkingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;

    private TextView freeLot1, freeLot2, avail1, avail2;

    private Firebase firebase1, firebase2;

    private LinearLayout parkinglot1, parkinglot2;

    private MarkerOptions options1, options2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getLocationPermission();

        parkinglot1 = findViewById(R.id.parkinglot1);
        parkinglot2 = findViewById(R.id.parkinglot2);

        options1 = new MarkerOptions().title("Parking lot 1").snippet("Food Court Parking Lot").position(new LatLng(20.2198034,85.7365638));
        options2 = new MarkerOptions().title("Parking lot 2").snippet("RIHC Building Parking Lot").position(new LatLng(20.221574,85.734611));

        parkinglot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Lot1Activity.class);
                startActivity(intent);
            }
        });

        parkinglot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Lot2Activity.class);
                startActivity(intent);
            }
        });

        freeLot1 = findViewById(R.id.freeLot1);
        freeLot2 = findViewById(R.id.freeLot2);
        avail1 = findViewById(R.id.avail1);
        avail2 = findViewById(R.id.avail2);
        firebase1 = new Firebase("https://fireapp-19296.firebaseio.com/space/1/free");
        firebase2 = new Firebase("https://fireapp-19296.firebaseio.com/space/2/free");

        firebase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String val = dataSnapshot.getValue(String.class);
                int vali = Integer.parseInt(val);
                if(vali == 0) {
                    freeLot1.setTextColor(Color.rgb(225, 37, 37));
                    avail1.setTextColor(Color.rgb(225, 37, 37));
                }
                else {
                    freeLot1.setTextColor(Color.rgb(37, 255, 37));
                    avail1.setTextColor(Color.rgb(37, 255, 37));
                }
                freeLot1.setText(val);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String val = dataSnapshot.getValue(String.class);
                int vali = Integer.parseInt(val);
                if(vali == 0) {
                    freeLot2.setTextColor(Color.rgb(225, 37, 37));
                    avail2.setTextColor(Color.rgb(225, 37, 37));
                }
                else {
                    freeLot2.setTextColor(Color.rgb(37, 255, 37));
                    avail2.setTextColor(Color.rgb(37, 255, 37));
                }
                freeLot2.setText(val);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.addMarker(options1);
            mMap.addMarker(options2);
        }

    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            Task location = fusedLocationProviderClient.getLastLocation();
            if(location != null) {
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");

                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Log.d(TAG, "getDeviceLocation: Error finding location");
            }
        }
        catch (SecurityException e) {
            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: Moving camera to current location");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            }
            else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

}
