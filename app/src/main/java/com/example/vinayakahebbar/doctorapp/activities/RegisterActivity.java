package com.example.vinayakahebbar.doctorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vinayakahebbar.doctorapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
    }
}
