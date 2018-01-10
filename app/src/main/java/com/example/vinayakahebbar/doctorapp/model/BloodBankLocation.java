package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class BloodBankLocation extends ModelView {
    private String name;
    private String path;

    public BloodBankLocation(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }
}
