package com.example.vsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsos.databinding.ActivityDashboardEditVehicleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Dashboard_EditVehicle extends AppCompatActivity {

    EditText updateRegNo, updateMakeModel, updateVehicleColor, updateVehicleYear;
    TextView updateVehicleData, cancelUpdate;

    ActivityDashboardEditVehicleBinding binding;
    DatabaseReference databaseReference;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardEditVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateRegNo = findViewById(R.id.updateRegNo);
        updateMakeModel = findViewById(R.id.updateMakeModel);
        updateVehicleColor = findViewById(R.id.updateVehicleColor);
        updateVehicleYear = findViewById(R.id.updateVehicleYear);

        updateVehicleData = findViewById(R.id.updateVehicleData);
        cancelUpdate = findViewById(R.id.cancelUpdate);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        ImageView BackArrow = findViewById(R.id.BackArrow);
        TextView BackArrowText = findViewById(R.id.BackArrowText);

        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_EditVehicle.this, Dashboard_myVehicle.class);
            startActivity(intent);
            finish();
        });


        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_EditVehicle.this, Dashboard_myVehicle.class);
            startActivity(intent);
            finish();
        });


        // OnClick Update Vehicle Details
        updateVehicleData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regNo = binding.updateRegNo.getText().toString();
                String regMakeModel = binding.updateMakeModel.getText().toString();
                String VehicleColor = binding.updateVehicleColor.getText().toString();
                String VehicleYear = binding.updateVehicleYear.getText().toString();

                UpdateData(regNo, regMakeModel, VehicleColor, VehicleYear);
            }
        });

    }

    private void UpdateData(String regNo, String regMakeModel, String vehicleColor, String vehicleYear) {

        HashMap Vehicle = new HashMap();
        Vehicle.put("regdNumber", regNo);
        Vehicle.put("vehicle_make_Model", regMakeModel);
        Vehicle.put("regd_vehicleColor", vehicleColor);
        Vehicle.put("regd_vehicleYear", vehicleYear);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(mUser.getUid());

        databaseReference.child("Vehicle Details").updateChildren(Vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    binding.updateRegNo.setText("");
                    binding.updateMakeModel.setText("");
                    binding.updateVehicleColor.setText("");
                    binding.updateVehicleYear.setText("");

                    Toast.makeText(Dashboard_EditVehicle.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Dashboard_EditVehicle.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}