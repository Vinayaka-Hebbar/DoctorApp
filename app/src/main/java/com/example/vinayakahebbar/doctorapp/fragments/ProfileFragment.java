package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.Patient;
import com.example.vinayakahebbar.doctorapp.utils.helper.NetworkHelper;
import com.example.vinayakahebbar.doctorapp.utils.UserManager;
import com.example.vinayakahebbar.doctorapp.utils.helper.SnackBarHelper;
import com.example.vinayakahebbar.doctorapp.utils.type.Gender;
import com.example.vinayakahebbar.doctorapp.utils.type.PatientType;
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
public class ProfileFragment extends Fragment implements ValueEventListener, View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private EditText editTextAge;
    private Button buttonSubmit;
    private FirebaseDatabase database;
    private RadioGroup radioGroupPatient;
    private RadioGroup radioGroupGender;
    private Spinner spinnerSpec;
    private ProgressDialog dialog;
    private DatabaseReference databaseReference;
    private EditText editTextInfo;
    private View view;
    private String spec;
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
        editTextInfo = (EditText)view.findViewById(R.id.et_patient_info);
        radioGroupPatient = (RadioGroup)view.findViewById(R.id.rg_profile_patient);
        radioGroupGender = (RadioGroup)view.findViewById(R.id.rg_profile_gender);
        spinnerSpec = (Spinner)view.findViewById(R.id.spinner_profile);
        dialog = new ProgressDialog(view.getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Fetching..");
        dialog.show();
        spinnerSpec.setOnItemSelectedListener(this);
        buttonSubmit.setOnClickListener(this);
        setUpDatabase();
        return view;
    }

    private void setUpDatabase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
        if (NetworkHelper.isNetworkAvailable(view.getContext()))
            databaseReference.child(UserManager.getUserId()).addValueEventListener(this);
        else

            new SnackBarHelper(view,"Internet error",Snackbar.LENGTH_SHORT).showError();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Patient value = dataSnapshot.getValue(Patient.class);
        if (value != null){
            editTextAge.setText(String.valueOf(value.getAge()));
            editTextInfo.setText(value.getInfo());
            switch (value.getGender()){
                case MALE:radioGroupGender.check(R.id.rb_profile_male);
                    break;
                case FEMALE:
                    radioGroupGender.check(R.id.rb_profile_female);
                    break;
            }
        }
        dialog.dismiss();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        dialog.dismiss();
        Snackbar snackbar = Snackbar.make(view,databaseError.getMessage(),Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.red_light));
        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        int age = Integer.parseInt(editTextAge.getText().toString());
        String info = editTextInfo.getText().toString();
        String uid = UserManager.getUserId();
        Gender gender = Gender.NONE;
        int genderId = radioGroupGender.getCheckedRadioButtonId();
        switch (genderId) {
            case R.id.rb_profile_male:
                gender = Gender.MALE;
                break;
            case R.id.rb_profile_female:
                gender = Gender.FEMALE;
                break;
        }
        PatientType type = PatientType.ME;
        int patientId = radioGroupPatient.getCheckedRadioButtonId();
        switch (patientId){
            case R.id.rb_profile_pnt_1:
                type = PatientType.ME;
                break;
            case R.id.rb_profile_pnt_2:
                type = PatientType.HUSBAND;
                break;
            case R.id.rb_profile_pnt_3:
                type = PatientType.WIFE;
                break;
            case R.id.rb_profile_pnt_4:
                type = PatientType.FATHER;
                break;
            case R.id.rb_profile_pnt_5:
                type = PatientType.MOTHER;
                break;
            case R.id.rb_profile_pnt_6:
                type = PatientType.DAUGHTER;
                break;
            case R.id.rb_profile_pnt_7:
                type = PatientType.SON;
                break;
            case R.id.rb_profile_pnt_8:
                type = PatientType.OTHERS;
                break;
        }
        Patient patient = new Patient(age,spec,info,type,gender);
        dialog.setMessage("Saving");
        dialog.show();
        databaseReference.child(uid).setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spec = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
