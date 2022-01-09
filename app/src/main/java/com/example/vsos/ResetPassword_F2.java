package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPassword_F2 extends AppCompatActivity {

    private TextView tryAnotherEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_f2);

        tryAnotherEmail = findViewById(R.id.tryAnotherEmail);
        tryAnotherEmail.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPassword_F2.this, ResetPassword.class);
            startActivity(intent);
        });

    }

    public void openApp(View view){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        if (launchIntent != null){
            startActivity(launchIntent);
        }else {
            Toast.makeText(ResetPassword_F2.this, "There is no such package", Toast.LENGTH_SHORT).show();
        }
    }
}