package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.number.IntegerWidth;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class Dashboard_accountInfo extends AppCompatActivity {

    private EditText mName, mMobile, mEmail;
    private TextView EditProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_account_info);

        mName = findViewById(R.id.fixedNameText);
        mName.setEnabled(false); //to disable it
//        mName.setOnClickListener(v -> {
//            mName.setFocusableInTouchMode(true); //to enable it
//        });

        mMobile = findViewById(R.id.fixedMobileText);
        mMobile.setEnabled(false);

        mEmail = findViewById(R.id.fixedEmailText);
        mEmail.setEnabled(false);

        TextView BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_accountInfo.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        TextView BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_accountInfo.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });


        //Logout Button
        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_accountInfo.this, LoggedOut.class);
            startActivity(intent);
            finish();
        });

        EditProfile = findViewById(R.id.EditProfile);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_accountInfo.this, Dashboard_Edit_Account_Info.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

