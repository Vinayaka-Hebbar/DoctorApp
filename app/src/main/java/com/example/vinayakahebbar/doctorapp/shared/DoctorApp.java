package com.example.vinayakahebbar.doctorapp.shared;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.TypedValue;

import com.example.vinayakahebbar.doctorapp.model.Patient;
import com.example.vinayakahebbar.doctorapp.utils.type.Gender;
import com.example.vinayakahebbar.doctorapp.utils.type.ObjectType;
import com.example.vinayakahebbar.doctorapp.utils.type.PatientType;

/**
 * Created by Vinayaka Hebbar on 11-01-2018.
 */

public class DoctorApp extends Application {
    public static SharedPreferences preferences;
    public static Patient patient;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        patient = new Patient();
        if (isSet("patient"))
            loadSettings();
    }

    private void loadSettings() {
        try {
            patient.setGender(Gender.valueOf(getValue("gender",TypedValue.TYPE_STRING).toString()));
            patient.setAge(getValue("age",TypedValue.TYPE_INT_DEC).getInt());
            patient.setInfo(getValue("info",TypedValue.TYPE_STRING).toString());
            patient.setType(PatientType.valueOf(getValue("type",TypedValue.TYPE_STRING).toString()));
            patient.setSpec(getValue("spec",TypedValue.TYPE_STRING).toString());
        }catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    public static ObjectType getValue(String key, int valueType){
        switch (valueType){
            case TypedValue.TYPE_STRING:
                return new ObjectType(preferences.getString(key,""));
            case TypedValue.TYPE_INT_DEC:
                return new ObjectType(preferences.getInt(key,0));
        }
        return null;
    }

    public static void setValue(String key,String value,int valueType){
        SharedPreferences.Editor editor = preferences.edit();
        switch (valueType){
            case TypedValue.TYPE_STRING:
                editor.putString(key,value);
                break;
            case TypedValue.TYPE_INT_DEC:
                editor.putInt(key,Integer.parseInt(value));
                break;
            case TypedValue.TYPE_INT_BOOLEAN:
                editor.putBoolean(key,Boolean.parseBoolean(value));
                break;
        }
        editor.apply();
    }

    public static boolean isSet(String key){
        if(preferences.contains("patient"))
            return true;
        return false;
    }

    @Override
    public void onTerminate() {
        saveState();
        super.onTerminate();
    }

    public static void saveState() {
        setValue("type",patient.getType().toString(),TypedValue.TYPE_STRING);
        setValue("info",patient.getInfo(),TypedValue.TYPE_STRING);
        setValue("spec",patient.getSpec(),TypedValue.TYPE_STRING);
        setValue("age",String.valueOf(patient.getAge()),TypedValue.TYPE_INT_DEC);
        setValue("gender",patient.getGender().toString(),TypedValue.TYPE_STRING);
    }

    public static void invalidate(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("patient");
        editor.apply();
    }

}
