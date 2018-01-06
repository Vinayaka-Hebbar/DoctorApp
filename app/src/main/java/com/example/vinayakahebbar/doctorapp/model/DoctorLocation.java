package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by Vinayaka Hebbar on 06-01-2018.
 */

public class DoctorLocation extends ModelView {
    public DoctorLocation(String name, String path) {
        this.name = name;
        this.path = path;
    }

    private String name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
