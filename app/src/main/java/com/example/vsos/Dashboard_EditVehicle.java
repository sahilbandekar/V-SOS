package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard_EditVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_edit_vehicle);

        ImageView BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_EditVehicle.this, Dashboard_myVehicle.class);
            startActivity(intent);
            finish();
        });

        TextView BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_EditVehicle.this, Dashboard_myVehicle.class);
            startActivity(intent);
            finish();
        });
    }
}