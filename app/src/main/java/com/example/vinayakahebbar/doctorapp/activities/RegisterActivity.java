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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText editTextEmail,editTextPass,editTextConfPass;
    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        editTextEmail = (EditText)findViewById(R.id.et_reg_email);
        editTextPass = (EditText)findViewById(R.id.et_reg_pass);
        editTextConfPass = (EditText)findViewById(R.id.et_reg_pass_conf);
        buttonRegister = (Button)findViewById(R.id.btn_reg);
        buttonRegister.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(final View v) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Signing Up");
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();
        String confPass = editTextConfPass.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("This field can' be empty");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            editTextPass.setError("This field can' be empty");
            return;
        }
        if (!pass.equals(confPass)) {
            editTextConfPass.setError("passwords not match");
            return;
        }
        if (pass.length() < 6) {
            editTextPass.setError("passwords must be min 4 char");
            return;
        }
        if (!email.matches("^[a-zA-Z0-9.]+@[a-zA-Z]+.(com|in)")) {
            editTextEmail.setError("Invalid Email");
            return;
        }
        new KeyboardHelper().hideKeyboard(this,getCurrentFocus());
        dialog.show();
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful())
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                else
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthUserCollisionException e){
                        Snackbar.make(v,e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });

    }
}
