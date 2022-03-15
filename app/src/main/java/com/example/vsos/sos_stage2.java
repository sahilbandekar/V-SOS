package com.example.vsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class sos_stage2 extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    ImageView BackArrow;
    View OpenMap;
    private TextView callTxt, callButton, callTxt1, callButton1, callTxt2, callButton2, BackArrowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_stage2);

        callTxt = findViewById(R.id.callTxt);
        callTxt1 = findViewById(R.id.callTxt1);
        callTxt2 = findViewById(R.id.callTxt2);

        callButton = findViewById(R.id.callButton);
        callButton1 = findViewById(R.id.callButton1);
        callButton2 = findViewById(R.id.callButton2);

        BackArrow = findViewById(R.id.BackArrow);
        BackArrowText = findViewById(R.id.BackArrowText);

        OpenMap = findViewById(R.id.OpenMap);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton();
            }
        });
        callButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton1();
            }
        });
        callButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton2();
            }
        });

        OpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sos_stage2.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sos_stage2.this, Homepage.class);
                startActivity(intent);
            }
        });

        BackArrowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sos_stage2.this, Homepage.class);
                startActivity(intent);
            }
        });
    }

    private void CallButton() {
        String number = callTxt.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(sos_stage2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(sos_stage2.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    private void CallButton1() {
        String number = callTxt1.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(sos_stage2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(sos_stage2.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    private void CallButton2() {
        String number = callTxt2.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(sos_stage2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(sos_stage2.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CallButton();
                CallButton1();
                CallButton2();
            } else {
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}