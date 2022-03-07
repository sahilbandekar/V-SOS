package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard_SOS_Problem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sos_problem);

        ImageView BackArrow = findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_SOS_Problem.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        TextView BackArrowText = findViewById(R.id.BackArrowText);
        BackArrowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_SOS_Problem.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        TextView btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_SOS_Problem.this, sos_stage2.class);
                startActivity(intent);
            }
        });

        // checkable

        CheckedTextView accident = findViewById(R.id.checked_accident);
        accident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accident.toggle();
                if (accident.isChecked()) {
                    Toast.makeText(Dashboard_SOS_Problem.this, "Accident", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Dashboard_SOS_Problem.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CheckedTextView checkedTextView = findViewById(R.id.textView_order);
        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedTextView.toggle();
                if (checkedTextView.isChecked()) {
                    Toast.makeText(Dashboard_SOS_Problem.this, "Other reason", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Dashboard_SOS_Problem.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CheckedTextView deadBattery = findViewById(R.id.textView_deadBattery);
        deadBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deadBattery.toggle();
                if (deadBattery.isChecked()) {
                    Toast.makeText(Dashboard_SOS_Problem.this, "Dead Battery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Dashboard_SOS_Problem.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}