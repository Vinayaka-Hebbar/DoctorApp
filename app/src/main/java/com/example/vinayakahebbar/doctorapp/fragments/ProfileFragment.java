package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.interfaces.JsonListener;
import com.example.vinayakahebbar.doctorapp.interfaces.NetworkListener;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.model.Patient;
import com.example.vinayakahebbar.doctorapp.shared.DoctorApp;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.utils.UserManager;
import com.example.vinayakahebbar.doctorapp.utils.helper.SnackBarHelper;
import com.example.vinayakahebbar.doctorapp.utils.type.Gender;
import com.example.vinayakahebbar.doctorapp.utils.type.PatientType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, JsonListener, NetworkListener {

    private EditText editTextAge;
    private Button buttonSubmit;
    private FirebaseDatabase database;
    private RadioGroup radioGroupPatient;
    private RadioGroup radioGroupGender;
    private Spinner spinnerSpec;
    private String[] array;
    private List<String> stringList;
    private ProgressDialog dialog;
    private EditText editTextInfo;
    private View view;
    private String spec;
    private String uid;
    public ProfileFragment() {
        // Required empty public constructor
        uid = UserManager.getUserId();
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
        array = view.getContext().getResources().getStringArray(R.array.specialities);
        stringList = Arrays.asList(array);
        spinnerSpec = (Spinner)view.findViewById(R.id.spinner_profile);
        dialog = new ProgressDialog(view.getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Fetching..");
        spinnerSpec.setOnItemSelectedListener(this);
        buttonSubmit.setOnClickListener(this);
        return view;
    }


    @Override
    public void onStart() {
        if (!DoctorApp.isSet("patient")) {
            HttpUtils httpUtils = new HttpUtils(new String[]{"Users", "retrieve.php"})
                    .setOnLoaded(this);
            httpUtils.sendJsonObject(uid);
            dialog.show();
        } else
            loadPatientDetails();
        super.onStart();
    }

    private void loadPatientDetails() {
        Patient patient = DoctorApp.patient;
        editTextAge.setText(String.valueOf(patient.getAge()));
        editTextInfo.setText(patient.getInfo());
        switch (patient.getGender()){
            case MALE:
                radioGroupGender.check(R.id.rb_profile_male);
                break;
            case FEMALE:
                radioGroupGender.check(R.id.rb_profile_female);
                break;
        }
        switch (patient.getType()){
            case ME:
                radioGroupPatient.check(R.id.rb_profile_pnt_1);
                break;
            case HUSBAND:
                radioGroupPatient.check(R.id.rb_profile_pnt_2);
                break;
            case WIFE:
                radioGroupPatient.check(R.id.rb_profile_pnt_3);
                break;
            case FATHER:
                radioGroupPatient.check(R.id.rb_profile_pnt_4);
                break;
            case MOTHER:
                radioGroupPatient.check(R.id.rb_profile_pnt_5);
                break;
            case DAUGHTER:
                radioGroupPatient.check(R.id.rb_profile_pnt_6);
                break;
            case SON:
                radioGroupPatient.check(R.id.rb_profile_pnt_7);
                break;
            case OTHERS:
                radioGroupPatient.check(R.id.rb_profile_pnt_8);
                break;
        }
        spinnerSpec.setSelection(stringList.indexOf(patient.getSpec()));
    }

    @Override
    public void onClick(View v) {
        int age = Integer.parseInt(editTextAge.getText().toString());
        String info = editTextInfo.getText().toString();
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
        switch (patientId) {
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
        DoctorApp.setValue("Hello","hello", TypedValue.TYPE_STRING);
        DoctorApp.patient = new Patient(age, spec, info, type, gender);
        dialog.setMessage("Saving");
        dialog.show();
        HttpUtils httpUtils = new HttpUtils(new String[]{"Users", "store.php"});
        httpUtils.setOnLoaded(new NetworkListener() {
            @Override
            public void onLoaded(String response) {
                DoctorApp.setValue("patient", "true", TypedValue.TYPE_INT_BOOLEAN);
                dialog.dismiss();
            }

            @Override
            public void onNetworkError(String error) {
                dialog.dismiss();
            }
        });
        try {
            httpUtils.sendJsonObject(DoctorApp.patient.toJsonObject(uid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spec = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onParsed(List<ModelView> lists) {
        if(lists.size() > 0) {
            DoctorApp.patient = (Patient) lists.get(0);
            DoctorApp.setValue("patient", "true", TypedValue.TYPE_INT_BOOLEAN);
            loadPatientDetails();
        }
        dialog.dismiss();
    }

    @Override
    public void onParseError(String error) {
        dialog.dismiss();
    }

    @Override
    public void onLoaded(String response) {
        JsonIO jsonIO = new JsonIO(response)
                .setOnLoaded(this);
        jsonIO.getPatientInfo();
    }

    @Override
    public void onNetworkError(String error) {
        dialog.dismiss();
        new SnackBarHelper(view,error, Snackbar.LENGTH_SHORT).showError();
    }
}
