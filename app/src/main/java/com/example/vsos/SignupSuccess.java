package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SignupSuccess extends AppCompatActivity {
    private br.com.simplepass.loading_button_lib.customViews.CircularProgressButton letsGetStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);

        letsGetStarted = findViewById(R.id.letsGetStarted);
        letsGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(SignupSuccess.this, Homepage.class);
            startActivity(intent);
            finish();
        });

    }
}