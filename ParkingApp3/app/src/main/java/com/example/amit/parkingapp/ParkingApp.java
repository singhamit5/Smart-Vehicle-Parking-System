package com.example.amit.parkingapp;

import android.app.Application;

import com.firebase.client.Firebase;

public class ParkingApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
