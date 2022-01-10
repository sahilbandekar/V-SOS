package com.example.vsos;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterMechanic extends AppCompatActivity {

    EditText inputName, inputEmail, inputMobileNumber, inputPassword;
    // String for Email Validation
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // Sreing for Mobile Number Validation
    String mobileNumberpattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private ImageView BackArrow;
    private TextView BackArrowText, CancelBtn, btnRegisterMechanic;

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


        // Onclick Listener for Register button
        btnRegisterMechanic.setOnClickListener(v -> PerformAuth());

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

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Correct Password");
        } else if (!number.matches(mobileNumberpattern)) {
            inputMobileNumber.setError("Enter Correct Mobile Number");
        } else {
            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Mechanic Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    // Send Verification Link
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    assert currentUser != null;
                    currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void unused) {
                            Toast.makeText(RegisterMechanic.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());

                        }
                    });

                    // Storing users data in database
                    UserClass userMap = new UserClass(email, password, name, number);
                    FirebaseUser user = task.getResult().getUser();
                    if (user == null) {
                        deleteCredential(credential);
                        return;
                    }

                    String userId = user.getUid();
                    FirebaseDatabase.getInstance().getReference().child("Mechanics")
                            .child(userId)
                            .setValue(userMap)
                            .addOnCompleteListener(task1 -> {
                                if (!task1.isSuccessful()) {
                                    return;
                                }
                                progressDialog.dismiss();
                                Toast.makeText(RegisterMechanic.this, "Mechanic Registration Successful", Toast.LENGTH_SHORT).show();
                                sendUserToNextActivity();
                            }).addOnFailureListener(RegisterMechanic.this, e -> deleteCredential(credential));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterMechanic.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    //    Delete User Credential
    private void deleteCredential(AuthCredential credential) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Unable to register", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseUser.delete().addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        return;
                    }
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Unable to register", Toast.LENGTH_SHORT).show();
                });
            });
        }
    }


    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterMechanic.this, EmailVerification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


// Pending work in this file
// 1. to add upload certificate functionality

