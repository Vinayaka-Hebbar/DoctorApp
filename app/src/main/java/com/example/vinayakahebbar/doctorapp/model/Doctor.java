package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by Vinayaka Hebbar on 04-01-2018.
 */

public class Doctor extends ModelView {
    private String name;

    public Doctor(String name, String spec) {
        this.name = name;
        this.spec = spec;
    }

    private String spec;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
