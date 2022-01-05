package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Register_Vehicle_Details extends AppCompatActivity {

    private TextView registerMechanic, BackArrowText;
    private ImageView BackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle_details);

        registerMechanic = findViewById(R.id.registerMechanic);
        registerMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Vehicle_Details.this, SignupSuccess.class);
                startActivity(intent);
            }
        });

        BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Vehicle_Details.this, RegisterVehicle.class);
                startActivity(intent);
            }
        });

        BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Vehicle_Details.this, RegisterVehicle.class);
                startActivity(intent);
            }
        });
    }
}