package com.example.vinayakahebbar.doctorapp.model;

/**
 * Created by Vinayaka Hebbar on 04-01-2018.
 */

public class ModelView {
    private int index;

    public ModelView(Object object) {
        this.object = object;
    }

    private Object object;

    public ModelView(int index) {
        this.index = index;
    }

    public ModelView(){

    }

    @Override
    public String toString() {
        return object.toString();
    }
}
