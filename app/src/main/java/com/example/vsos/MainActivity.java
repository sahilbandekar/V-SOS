package com.example.vsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


// This Activity i.e., ( MainActivity ) is used as --- SplashScreen --- Activity


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;

//    Animation of splash screen
    Animation topAnim, bottomAnim;
    ImageView logo, mycar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


//        Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//         Hooks
        logo = findViewById(R.id.logo);
        mycar = findViewById(R.id.mycar);

        logo.setAnimation(topAnim);
        mycar.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);   // after loading splash screen Dashboard activity will
                // be loaded and it can be changed to whatever activity we want
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

}