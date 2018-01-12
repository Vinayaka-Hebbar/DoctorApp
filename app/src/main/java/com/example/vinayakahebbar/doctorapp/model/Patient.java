package com.example.vinayakahebbar.doctorapp.model;

import com.example.vinayakahebbar.doctorapp.utils.type.Gender;
import com.example.vinayakahebbar.doctorapp.utils.type.PatientType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vinayaka Hebbar on 06-01-2018.
 */

public class Patient extends ModelView {
    private int age;

    public Patient(int age, String spec,String info, PatientType type, Gender gender) {
        this.age = age;
        this.spec = spec;
        this.info = info;
        this.type = type;
        this.gender = gender;
    }

    private String spec;
    private PatientType type;

    private String info;
    private Gender gender;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Patient(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PatientType getType() {
        return type;
    }

    public void setType(PatientType type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public Patient(){

    }

    public JSONObject toJsonObject(String name) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject patient = new JSONObject();
        patient.put("type",type);
        patient.put("age",age);
        patient.put("gender",gender);
        patient.put("info",info);
        patient.put("specialization",spec);
        jsonObject.put("name",name);
        jsonObject.put(name,patient);
        return jsonObject;
    }

    @Override
    public String toString() {
        return age + "";
    }
}
