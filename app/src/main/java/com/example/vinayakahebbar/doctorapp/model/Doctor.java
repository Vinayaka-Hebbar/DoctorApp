package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by US76 on 04-01-2018.
 */

public class Doctor extends ModelView {
    private String name;
    private long phone_no;
    private String specialization;
    private String information;

    public Doctor(String name, String address) {
        this.name = name;
        this.address = address;
    }

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(long phone_no) {
        this.phone_no = phone_no;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
