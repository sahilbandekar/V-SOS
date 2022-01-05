package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp_Error extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_error);

        ImageView BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp_Error.this, Login.class);
            startActivity(intent);
            finish();
        });

        TextView BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp_Error.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}