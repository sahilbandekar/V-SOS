package com.example.vsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private LinearLayout wholeBackButton;
    private TextView resetPasswordMail;
    EditText txtEmail;
    String email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        wholeBackButton = findViewById(R.id.wholeBackButton);
        resetPasswordMail = findViewById(R.id.resetPasswordMail);

        txtEmail = findViewById(R.id.email);


        wholeBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPassword.this, Login.class);
            startActivity(intent);
        });


        resetPasswordMail.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPassword.this, ResetPassword_F2.class);
            startActivity(intent);
        });


        auth = FirebaseAuth.getInstance();


        resetPasswordMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {
        email = txtEmail.getText().toString();
        if (email.isEmpty())
        {
            txtEmail.setError("Required");
        }else {
            forgotPass();
        }
    }

    private void forgotPass() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPassword.this, ResetPassword_F2.class));
                    finish();
                }else{
                    Toast.makeText(ResetPassword.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}