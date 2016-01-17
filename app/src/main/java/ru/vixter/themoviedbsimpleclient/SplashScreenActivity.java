package ru.vixter.themoviedbsimpleclient;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


/*
 * Copyright (c): Created by Vixter Infected on 17.1.2016.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = (int) TimeUnit.SECONDS.toMillis(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashActivity();
    }

    public void splashActivity(){
        ((TextView)findViewById(R.id.splashText)).setTypeface(
                Typeface.createFromAsset(this.getAssets(), "fonts/American Captain.ttf"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
