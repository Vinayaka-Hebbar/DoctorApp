package com.example.vinayakahebbar.doctorapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayakahebbar.doctorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText editTextEmail,editTextPass,editTextConfPass;
    private Button buttonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonReg = (Button)findViewById(R.id.btn_reg);
        editTextEmail = (EditText)findViewById(R.id.et_reg_email);
        editTextPass = (EditText)findViewById(R.id.et_reg_pass);
        editTextConfPass = (EditText)findViewById(R.id.et_reg_cong_pass);
        buttonReg.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();
        String confPass = editTextConfPass.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confPass))
        {
            editTextEmail.setError("This Field can't be empty");
            editTextPass.setError("This Field can't be empty");
            editTextConfPass.setError("This Field can't be empty");
            return;
        }
        if(!pass.equals(confPass))
        {
            editTextConfPass.setError("Passwords not match");
            return;
        }
        if(!email.matches("^[a-zA-Z0-9.]+@[a-zA-Z]+.(com|in)"))
        {
            editTextEmail.setError("Email not valid");
            return;
        }
        if(pass.length() < 4){
            editTextPass.setError("Password must be min 4");
            return;
        }
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
