package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard_Edit_Account_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_edit_account_info);

        TextView BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_Edit_Account_Info.this, Dashboard_accountInfo.class);
            startActivity(intent);
            finish();
        });

        TextView BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_Edit_Account_Info.this, Dashboard_accountInfo.class);
            startActivity(intent);
            finish();
        });

        // Save Changes
        TextView SaveChanges = findViewById(R.id.SaveChanges);
        SaveChanges.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_Edit_Account_Info.this, Dashboard_accountInfo.class);
            startActivity(intent);
            finish();
        });
    }
}