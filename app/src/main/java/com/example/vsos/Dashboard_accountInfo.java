package com.example.vsos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Dashboard_accountInfo extends AppCompatActivity {



    EditText fullName, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_account_info);

        TextView BackArrow = findViewById(R.id.BackArrow);
        TextView BackArrowText = findViewById(R.id.BackArrowText);
        TextView logout = findViewById(R.id.logout);

        phone = findViewById(R.id.profilePhone);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.profileEmail);


        fullName.setEnabled(false); //to disable
        phone.setEnabled(false);
        email.setEnabled(false);

        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_accountInfo.this, Homepage.class);
            startActivity(intent);
//                finish();
        });
        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_accountInfo.this, Homepage.class);
            startActivity(intent);
//                finish();
        });

        //Logout Button - Making a user SignOut
        logout.setOnClickListener(v -> {
            if (v.getId() == R.id.logout) {
                AuthUI.getInstance()
                        .signOut(Dashboard_accountInfo.this)
                        .addOnCompleteListener(task -> {
                            // user is now signed out
                            startActivity(new Intent(Dashboard_accountInfo.this, LoggedOut.class));

                            finish();
                        });
            }
        });

        // Fetching user's data
        firebaseMethod();

        // Fetching mechanic data
        mechanicFirebaseMethod();

    }


    // Displaying mechanic information
    private void mechanicFirebaseMethod() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid == null) {
            return;
        }
        Log.d("ContentValues", uid);
        FirebaseDatabase.getInstance().getReference().child("Mechanics")
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    return;
                }
                UserClass users = snapshot.getValue(UserClass.class);
                if (users == null) {
                    return;
                }
                email.setText(users.getEmail());
                if (users.getPhoneNumber().equals("default")) {
                    phone.setVisibility(View.GONE);
                } else {
                    phone.setText(users.getPhoneNumber());
                }
                fullName.setText(users.getName().toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Displaying User's information
    private void firebaseMethod() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid == null) {
            return;
        }
        Log.d("ContentValues", uid);
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    return;
                }
                UserClass users = snapshot.getValue(UserClass.class);
                if (users == null) {
                    return;
                }
                email.setText(users.getEmail());
                if (users.getPhoneNumber().equals("default")) {
                    phone.setVisibility(View.GONE);
                } else {
                    phone.setText(users.getPhoneNumber());
                }
                fullName.setText(users.getName().toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

