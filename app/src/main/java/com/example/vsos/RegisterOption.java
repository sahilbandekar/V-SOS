package com.example.vsos;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterOption extends AppCompatActivity {

    private TextView regMechanic;
    private TextView AlreadyLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_option);


        // OnClick event when clicked on REGISTER AS USER
        TextView regUser = findViewById(R.id.regUser);
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterOption.this, SignUp.class);
                startActivity(intent);
            }
        });


        // OnClick event when clicked on REGISTER AS MECHANIC
        regMechanic = findViewById(R.id.regMechanic);
        regMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterOption.this, RegisterMechanic.class);  // add mechanic signup here !!
                startActivity(intent);
            }
        });


        // OnClick event when clicked on Already have an account? LOGIN
        AlreadyLogin = findViewById(R.id.AlreadyLogin);
        AlreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterOption.this, Login.class);
                startActivity(intent);
            }
        });


        // Making user stay Logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String uid = user.getUid();
            if (uid == null) {
                Toast.makeText(this, "UID not valid", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Toast.makeText(RegisterOption.this, "User not exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UserClass users = snapshot.getValue(UserClass.class);
                    if (users == null) {
                        Toast.makeText(RegisterOption.this, "User not exist ER#2", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (users.getIsVerified() == 1) {
                        //user verified allow app use
                        Intent intent = new Intent(RegisterOption.this, Homepage.class);
                        startActivity(intent);
                    } else {
                        //user not verified are redirect to verify screen
                        Intent intent = new Intent(RegisterOption.this, EmailVerification.class);
                        startActivity(intent);
                    }
                    // Toast.makeText(RegisterOption.this, "User verification status" + users.getIsVerified(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }


    }
}
