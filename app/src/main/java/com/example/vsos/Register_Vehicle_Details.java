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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Vehicle_Details extends AppCompatActivity {

    EditText RegdOwner, RegdNo, make_Model, vehicleColor, vehicleYear;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private TextView registerVehicle, BackArrowText;
    private ImageView BackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle_details);

        // Vehicle Details variables
        RegdOwner = findViewById(R.id.RegdOwner);
        RegdNo = findViewById(R.id.RegdNo);
        make_Model = findViewById(R.id.make_Model);
        vehicleColor = findViewById(R.id.vehicleColor);
        vehicleYear = findViewById(R.id.vehicleYear);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        registerVehicle = findViewById(R.id.registerVehicle);
        registerVehicle.setOnClickListener(v -> storeVehicleDetails());

        BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Vehicle_Details.this, RegisterVehicle.class);
                startActivity(intent);
            }
        });

        BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Vehicle_Details.this, RegisterVehicle.class);
                startActivity(intent);
            }
        });
    }

    private void storeVehicleDetails() {
        String RegdownerName = RegdOwner.getText().toString();
        String RegdNumber = RegdNo.getText().toString();
        String Vehicle_make_Model = make_Model.getText().toString();
        String Regd_vehicleColor = vehicleColor.getText().toString();
        String Regd_vehicleYear = vehicleYear.getText().toString();
        // fields should not be empty
        if (RegdownerName.isEmpty()) {
            RegdOwner.setError("Regd Owner should not be empty.");
        } else if (RegdNumber.isEmpty()) {
            RegdNo.setError("Regd number should not be empty.");
        } else if (Vehicle_make_Model.isEmpty()) {
            make_Model.setError("Make/Model should not be empty.");
        } else if (Regd_vehicleColor.isEmpty()) {
            vehicleColor.setError("Vehicle colour should not be empty.");
        } else if (Regd_vehicleYear.isEmpty()) {
            vehicleYear.setError("Vehicle year should not be empty.");
        } else {
            progressDialog = new ProgressDialog(Register_Vehicle_Details.this);
            progressDialog.setMessage("Please wait while vehicle registration...");
            progressDialog.setTitle("Vehicle Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            vehicleClass vehicleMap = new vehicleClass(RegdownerName, RegdNumber, Vehicle_make_Model, Regd_vehicleColor, Regd_vehicleYear);
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentFirebaseUser != null) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Vehicle Details")
                        //ToDo
                        // .child(vehicleMap.getRegdNumber())
                        .setValue(vehicleMap)
                        .addOnCompleteListener(task1 -> {
                            if (!task1.isSuccessful()) {
                                return;
                            }
                            progressDialog.dismiss();
                            Toast.makeText(Register_Vehicle_Details.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            sendUserToNextActivity();
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }


        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(Register_Vehicle_Details.this, SignupSuccess.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
