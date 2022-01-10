package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoggedOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_out);

        // Hooking Social media login variables
        TextView btnGoogle = findViewById(R.id.btnGoogle);
        TextView btnFacebook = findViewById(R.id.btnFacebook);

        // Setting OnClick Listener on Google login button
        btnGoogle.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedOut.this, GoogleSignInActivity.class);
            startActivity(intent);
        });

        // Setting OnClick Listener on Facebook login button
        btnFacebook.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedOut.this, FacebookAuthActivity.class);
            startActivity(intent);
        });
    }
}