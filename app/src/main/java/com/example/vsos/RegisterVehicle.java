package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterVehicle extends AppCompatActivity {

    private br.com.simplepass.loading_button_lib.customViews.CircularProgressButton GetStarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);


        GetStarted = findViewById(R.id.GetStarted);
        GetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterVehicle.this, Register_Vehicle_Details.class);
                startActivity(intent);
            }
        });
    }
}