package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class BloodBank extends ModelView {
    private String name;
    private String phoneNo;
    private String address;
    private String timing;

    public BloodBank(String name, String phoneNo, String address, String timing) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
        this.timing = timing;
    }

    public BloodBank() {
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public String getTiming() {
        return timing;
    }
}
