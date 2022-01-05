package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard_myVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_my_vehicle);

        ImageView BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_myVehicle.this, Homepage.class);
                startActivity(intent);
//                finish();
            }
        });

        TextView BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_myVehicle.this, Homepage.class);
                startActivity(intent);
//                finish();
            }
        });

    }

    public void onEditVehicleDetailsClick(View view) {
        Intent intent = new Intent(Dashboard_myVehicle.this, Dashboard_EditVehicle.class);
        startActivity(intent);
        finish();
    }
}