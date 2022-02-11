package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmailVerification extends AppCompatActivity {

    TextView TryAnotherEmail, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        TryAnotherEmail = findViewById(R.id.tryAnotherEmail);
        TryAnotherEmail.setOnClickListener(v -> {
            Intent intent = new Intent(EmailVerification.this, RegisterOption.class);
            startActivity(intent);
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(EmailVerification.this, SignUp.class);
            startActivity(intent);
        });

    }

    public void openApp(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(EmailVerification.this, "There is no such package", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EmailVerification.this, SignUp_Error.class);
            startActivity(intent);
        }
    }
}