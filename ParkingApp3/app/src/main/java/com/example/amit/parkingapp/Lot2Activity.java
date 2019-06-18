package com.example.amit.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Lot2Activity extends Activity {

    private Firebase firebase2, firebase12, firebase22, firebase32;
    private Button back_btn2;
    private CardView a12, a22, a32;
    private TextView oc2, av2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot2);

        oc2 = findViewById(R.id.oc2);
        av2 = findViewById(R.id.av2);

        back_btn2 = findViewById(R.id.back_btn2);
        back_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lot2Activity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        a12 = findViewById(R.id.a12);

        firebase12 = new Firebase("https://fireapp-19296.firebaseio.com/parking/2/A1/status");

        firebase12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("1"))
                    a12.setCardBackgroundColor(Color.rgb(204,0,0));
                else
                    a12.setCardBackgroundColor(Color.rgb(102,153,0));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        a22 = findViewById(R.id.a22);

        firebase22 = new Firebase("https://fireapp-19296.firebaseio.com/parking/2/A2/status");

        firebase22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("1"))
                    a22.setCardBackgroundColor(Color.rgb(204,0,0));
                else
                    a22.setCardBackgroundColor(Color.rgb(102,153,0));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        a32 = findViewById(R.id.a32);

        firebase32 = new Firebase("https://fireapp-19296.firebaseio.com/parking/2/A3/status");

        firebase32.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("1"))
                    a32.setCardBackgroundColor(Color.rgb(204,0,0));
                else
                    a32.setCardBackgroundColor(Color.rgb(102,153,0));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        firebase2 = new Firebase("https://fireapp-19296.firebaseio.com/space/2/free");

        firebase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String val = dataSnapshot.getValue(String.class);
                av2.setText(val);
                oc2.setText(Integer.toString(3 - Integer.parseInt(val)));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
