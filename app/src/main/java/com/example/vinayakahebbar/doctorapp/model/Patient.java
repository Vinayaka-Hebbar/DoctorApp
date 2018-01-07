package com.example.vinayakahebbar.doctorapp.model;

import com.example.vinayakahebbar.doctorapp.utils.type.Gender;
import com.example.vinayakahebbar.doctorapp.utils.type.PatientType;

/**
 * Created by Vinayaka Hebbar on 06-01-2018.
 */

public class Patient {
    private int age;

    public Patient(int age, String spec, PatientType info, Gender gender) {
        this.age = age;
        this.spec = spec;
        this.info = info;
        this.gender = gender;
    }

    private String spec;
    private PatientType info;
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

    public Patient(){

    }

    @Override
    public String toString() {
        return age + "";
    }

    public PatientType getInfo() {
        return info;
    }

    public void setInfo(PatientType info) {
        this.info = info;
    }
}
