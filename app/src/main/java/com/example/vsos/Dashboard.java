package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button loginWindow = findViewById(R.id.loginWindow);
        loginWindow.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
        });

        Button registerOption = findViewById(R.id.registerOption);
        registerOption.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, RegisterOption.class);
            startActivity(intent);
        });

        Button demo = findViewById(R.id.demo);
        demo.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, Homepage.class);
            startActivity(intent);
        });

    }
}

