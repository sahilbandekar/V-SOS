package com.example.vsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard_myVehicle extends AppCompatActivity {

    TextView registeredVehicleNumber, registeredMake_Model, registeredVehicleColor, registeredVehicleYear;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_my_vehicle);

        database = FirebaseDatabase.getInstance();
        database.goOnline();

        reference = database.getReference();
        // Hooking Variables
        registeredVehicleNumber = findViewById(R.id.registeredVehicleNumber);
        registeredMake_Model = findViewById(R.id.registeredMake_Model);
        registeredVehicleColor = findViewById(R.id.registeredVehicleColor);
        registeredVehicleYear = findViewById(R.id.registeredVehicleYear);

        ImageView BackArrow = findViewById(R.id.BackArrow);
        TextView BackArrowText = findViewById(R.id.BackArrowText);

        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_myVehicle.this, Homepage.class);
            startActivity(intent);
        });


        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_myVehicle.this, Homepage.class);
            startActivity(intent);
        });

        // Fetching Vehicle Details
        displayVehicleDetails();
    }

    public void onEditVehicleDetailsClick(View view) {
        Intent intent = new Intent(Dashboard_myVehicle.this, Dashboard_EditVehicle.class);
        startActivity(intent);
        finish();
    }


    // Displaying User's Vehicle information
    private void displayVehicleDetails() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid == null) {
            return;
        }
        Log.d("ContentValues", uid);
        reference.child("Users")
                .child(uid)
                .child("Vehicle Details")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            return;
                        }
                        vehicleClass carClass = snapshot.getValue(vehicleClass.class);
                        if (carClass == null) {
                            return;
                        }

                        registeredVehicleNumber.setText(carClass.getRegdNumber());
                        registeredMake_Model.setText(carClass.getVehicle_make_Model());
                        registeredVehicleColor.setText(carClass.getRegd_vehicleColor());
                        registeredVehicleYear.setText(carClass.getRegd_vehicleYear());
                        Log.d("ContentValues", carClass.getRegdNumber().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("ContentValues", error.getMessage());
                    }
                });
    }

}