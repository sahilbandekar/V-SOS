package com.example.vsos;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText inputName, inputEmail, inputMobileNumber, inputPassword;
    br.com.simplepass.loading_button_lib.customViews.CircularProgressButton btnRegister;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobileNumberpattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        changeStatusBarColor();


        // Hooking Variables
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputMobileNumber = findViewById(R.id.inputMobileNumber);
        inputPassword = findViewById(R.id.inputPassword);
        btnRegister = findViewById(R.id.cirRegisterButton);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Onclick Listener for Register button
        btnRegister.setOnClickListener(v -> PerformAuth());

        // Google
        ImageView btnGoogle = findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, GoogleSignInActivity.class);
            startActivity(intent);
        });

        // Facebook
        ImageView btnFacebook = findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, FacebookAuthActivity.class);
            startActivity(intent);
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
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

//                  Send Verification Link
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    assert currentUser != null;
                    currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void unused) {
                            Toast.makeText(SignUp.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());

                        }
                    });


                    UserClass userMap = new UserClass(email, password, name, number, 0);

                    FirebaseUser user = task.getResult().getUser();
                    if (user == null) {
                        deleteCredential(credential);
                        return;
                    }

                    String userId = user.getUid();

                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(userId)
                            .setValue(userMap)
                            .addOnCompleteListener(task1 -> {
                                if (!task1.isSuccessful()) {
                                    return;
                                }
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendUserToNextActivity();
                            }).addOnFailureListener(SignUp.this, e -> deleteCredential(credential));

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
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
        String user = "User";
        Intent intent = new Intent(SignUp.this, RegisterVehicle.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void changeStatusBarColor() {

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    // Sliding  Animation
    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}