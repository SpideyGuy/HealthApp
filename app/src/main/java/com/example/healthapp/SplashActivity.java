package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    final String PREFERENCES_NAME = "user_data";

    final String IS_LOGGED_IN = "logged_in";

    ImageView imgLogo;
    TextView tvAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        tvAppName = (TextView) findViewById(R.id.tv_app_name);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        imgLogo.startAnimation(animFadeIn);
        tvAppName.startAnimation(animFadeIn);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(sharedPreferences.getBoolean(IS_LOGGED_IN,false)){
                        startActivity(new Intent(SplashActivity.this, LoginRegisterActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginRegisterActivity.class));
                    }
                    finish();
                }
            }
        };

        timer.start();
    }
}