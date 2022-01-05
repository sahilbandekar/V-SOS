package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PasswordChangedSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_changed_success);


        TextView loginNow = findViewById(R.id.loginNow);
        loginNow.setOnClickListener(v -> {
            Intent intent = new Intent(PasswordChangedSuccess.this, Login.class);
            startActivity(intent);
        });

    }
}