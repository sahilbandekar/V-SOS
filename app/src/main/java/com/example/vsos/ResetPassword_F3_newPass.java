package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResetPassword_F3_newPass extends AppCompatActivity {

    private ImageView BackArrow;
    private TextView BackArrowText, createNewPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_f3_new_pass);

        BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword_F3_newPass.this, ResetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPassword_F3_newPass.this, ResetPassword.class);
            startActivity(intent);
            finish();
        });


        createNewPassBtn = findViewById(R.id.createNewPassBtn);
        createNewPassBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPassword_F3_newPass.this, PasswordChangedSuccess.class);
            startActivity(intent);
            finish();
        });


    }
}