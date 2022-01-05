package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

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
                Intent intent = new Intent(RegisterOption.this, Login.class);
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


    }
}
