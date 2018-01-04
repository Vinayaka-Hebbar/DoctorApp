package com.example.vinayakahebbar.doctorapp.activities;

import android.app.ProgressDialog;
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
import com.example.vinayakahebbar.doctorapp.utils.KeyboardHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText editTextEmail,editTextPass;
    private Button buttonLogin,buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        buttonLogin = (Button)findViewById(R.id.btn_login);
        buttonSignUp = (Button)findViewById(R.id.btn_sign_up);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        editTextEmail = (EditText)findViewById(R.id.et_email);
        editTextPass = (EditText)findViewById(R.id.et_pass);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        final ProgressDialog dialog = new ProgressDialog(this);
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();
        dialog.setMessage("Logging in");
        if(TextUtils.isEmpty(email))
        {
            editTextEmail.setError("This field can't be empty");
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            editTextPass.setError("This field can't be empty");
            return;
        }
        new KeyboardHelper().hideKeyboard(this,getCurrentFocus());
        dialog.show();
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    try {
                        dialog.dismiss();
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Snackbar.make(v,e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
