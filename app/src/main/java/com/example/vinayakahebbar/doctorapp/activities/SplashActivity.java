package com.example.vinayakahebbar.doctorapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vinayakahebbar.doctorapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity implements Runnable {

    private static final int TIME_OUT = 4000;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this,TIME_OUT);
    }

    @Override
    public void run() {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null)
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        else
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
