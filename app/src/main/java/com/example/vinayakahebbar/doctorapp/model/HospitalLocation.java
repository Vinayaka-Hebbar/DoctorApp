package com.example.vinayakahebbar.doctorapp.model;


public class HospitalLocation extends ModelView {
    public HospitalLocation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String name;
    private String address;
}
