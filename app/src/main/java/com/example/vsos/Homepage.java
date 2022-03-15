package com.example.vsos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    androidx.cardview.widget.CardView myVehicle, helpMe, fuelStation, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeage);
        myVehicle = findViewById(R.id.myVehicle);
        myVehicle.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Dashboard_myVehicle.class);
            startActivity(intent);
            finish();
        });

        helpMe = findViewById(R.id.helpMe);
        helpMe.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, sos_stage2.class);
            startActivity(intent);
            finish();
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Dashboard_accountInfo.class);
            startActivity(intent);
            finish();
        });

    }
}

