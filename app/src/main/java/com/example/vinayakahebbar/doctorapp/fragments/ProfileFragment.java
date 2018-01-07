package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.Patient;
import com.example.vinayakahebbar.doctorapp.utils.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ValueEventListener, View.OnClickListener {

    private EditText editTextAge;
    private Button buttonSubmit;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private View view;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        editTextAge = (EditText)view.findViewById(R.id.et_profile_age);
        buttonSubmit = (Button)view.findViewById(R.id.btn_profile_submit);
        buttonSubmit.setOnClickListener(this);
        setUpDatabase();
        return view;
    }

    private void setUpDatabase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
        databaseReference.child(UserManager.getUserId() ).addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Patient value = dataSnapshot.getValue(Patient.class);
        if (value != null){
            editTextAge.setText(String.valueOf(value.getAge()));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onClick(View v) {
        int age = Integer.parseInt(editTextAge.getText().toString());
        String uid = UserManager.getUserId();
        Patient patient = new Patient(age);
        databaseReference.child(uid).setValue(patient);
    }
}
