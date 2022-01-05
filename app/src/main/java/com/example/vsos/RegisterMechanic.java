package com.example.vsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class RegisterMechanic extends AppCompatActivity {

    private ImageView BackArrow;
    private TextView BackArrowText, CancelBtn, btnRegisterMechanic;

    EditText inputName, inputEmail, inputMobileNumber, inputPassword;

    // String for Email Validation
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String mobileNumberpattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mechanic);

        // Hooking the variables
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputMobileNumber = findViewById(R.id.inputMobileNumber);
        inputPassword = findViewById(R.id.inputPassword);
        btnRegisterMechanic = findViewById(R.id.btnRegisterMechanic);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



        btnRegisterMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });

        BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterMechanic.this, RegisterOption.class);
            startActivity(intent);
            finish();
        });

        BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterMechanic.this, RegisterOption.class);
            startActivity(intent);
            finish();
        });


        CancelBtn = findViewById(R.id.CancelBtn);
        CancelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterMechanic.this, RegisterOption.class);
            startActivity(intent);
            finish();
        });

    }

    private void PerformAuth() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String number = inputMobileNumber.getText().toString();
        String password = inputPassword.getText().toString();

        if (!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email");
        }else if (password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter Correct Password");
        }else if (!number.matches(mobileNumberpattern))
        {
            inputMobileNumber.setError("Enter Correct Mobile Number");
        }
        else {
            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Mechanic Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterMechanic.this, "Mechanic Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterMechanic.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterMechanic.this, SignupSuccess.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


// Pending work in this file
// 1. to add upload certificate functionality

