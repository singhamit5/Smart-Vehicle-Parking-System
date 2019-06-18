package com.example.amit.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Lot1Activity extends Activity {

    private Firebase firebase1, firebase11, firebase21, firebase31;
    private Button back_btn1;
    private CardView a11, a21, a31;
    private TextView oc1, av1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot1);

        oc1 = findViewById(R.id.oc1);
        av1 = findViewById(R.id.av1);

        back_btn1 = findViewById(R.id.back_btn1);
        back_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lot1Activity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        a11 = findViewById(R.id.a11);

        firebase11 = new Firebase("https://fireapp-19296.firebaseio.com/parking/1/A1/status");

        firebase11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("1"))
                    a11.setCardBackgroundColor(Color.rgb(204,0,0));
                else
                    a11.setCardBackgroundColor(Color.rgb(102,153,0));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        a21 = findViewById(R.id.a21);

        firebase21 = new Firebase("https://fireapp-19296.firebaseio.com/parking/1/A2/status");

        firebase21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("1"))
                    a21.setCardBackgroundColor(Color.rgb(204,0,0));
                else
                    a21.setCardBackgroundColor(Color.rgb(102,153,0));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        a31 = findViewById(R.id.a31);

        firebase31 = new Firebase("https://fireapp-19296.firebaseio.com/parking/1/A3/status");

        firebase31.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("1"))
                    a31.setCardBackgroundColor(Color.rgb(204,0,0));
                else
                    a31.setCardBackgroundColor(Color.rgb(102,153,0));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase1 = new Firebase("https://fireapp-19296.firebaseio.com/space/1/free");

        firebase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String val = dataSnapshot.getValue(String.class);
                av1.setText(val);
                oc1.setText(Integer.toString(3 - Integer.parseInt(val)));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
