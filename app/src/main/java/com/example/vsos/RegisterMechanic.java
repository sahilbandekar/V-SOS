package com.example.vsos;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class RegisterMechanic extends AppCompatActivity {

    EditText inputName, inputEmail, inputMobileNumber;
    com.google.android.material.textfield.TextInputEditText inputPassword;
    FrameLayout btn_upload;

    // String for Email Validation
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    // String for Mobile Number Validation
    String mobileNumberpattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    // Storage
    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mechanic);

        // Hooking the variables
        // Edit Text fields
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputMobileNumber = findViewById(R.id.inputMobileNumber);
        inputPassword = findViewById(R.id.inputPassword);

        // Upload
        btn_upload = findViewById(R.id.btn_upload);

        // Register
        TextView btnRegisterMechanic = findViewById(R.id.btnRegisterMechanic);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Calling Storage and Database Instance
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        // Onclick Listener for Register button
        btnRegisterMechanic.setOnClickListener(v -> PerformAuth());

        // OnClickListener on BackArrow Icon
        ImageView backArrow = findViewById(R.id.BackArrow);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterMechanic.this, RegisterOption.class);
            startActivity(intent);
            finish();
        });

        // OnClickListener on BackArrow Text
        TextView backArrowText = findViewById(R.id.BackArrowText);
        backArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterMechanic.this, RegisterOption.class);
            startActivity(intent);
            finish();
        });

        // OnClick for Upload btn
        btn_upload.setOnClickListener(v -> selectPDFFile());

        // Cancel button
        TextView cancelBtn = findViewById(R.id.CancelBtn);
        cancelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterMechanic.this, RegisterOption.class);
            startActivity(intent);
            finish();
        });

    }

    // Selecting Local file / PDF Document from user's device
    private void selectPDFFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select"), 1);
    }

    private void PerformAuth() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String number = inputMobileNumber.getText().toString();
        String password = Objects.requireNonNull(inputPassword.getText()).toString();

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
                    currentUser.sendEmailVerification().addOnSuccessListener(unused -> Toast.makeText(RegisterMechanic.this,
                            "Verification Email has been sent", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Log.d(TAG, "onFailure: Email not sent " + e.getMessage()));

                    // Storing users data in database
                    UserClass userMap = new UserClass(email, password, name, number, 0);
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

    // Sending user to next activity after Registration
    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterMechanic.this, SignupSuccess.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // Uploading file code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            uploadPDFFile(data.getData());
        }

    }

    private void uploadPDFFile(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {

                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete()) ;
                    Uri url = uri.getResult();

                    Toast.makeText(RegisterMechanic.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }).addOnProgressListener(snapshot -> {

            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            progressDialog.setMessage("Uploaded: " + (int) progress + "%");
        });

    }
}