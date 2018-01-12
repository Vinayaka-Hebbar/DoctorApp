package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by lenovo on 09-01-2018.
 */

public class EmergencyContact {
    private String name;
    private long PhNo;

    public EmergencyContact(String name, long phNo) {
        this.name = name;
        PhNo = phNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhNo() {
        return PhNo;
    }

    public void setPhNo(long phNo) {
        PhNo = phNo;
    }
}