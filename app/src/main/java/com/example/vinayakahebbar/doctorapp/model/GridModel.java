package com.example.vinayakahebbar.doctorapp.model;

import com.example.vinayakahebbar.doctorapp.utils.type.GridType;

/**
 * Created by lenovo on 07-01-2018.
 */

public class GridModel {
    private String text;
    private int resourceId;
    private GridType type;

    public String getText() {
        return text;
    }

    public int getResourceId() {
        return resourceId;
    }

    public GridType getType() {
        return type;
    }

    public GridModel(String text, int resourceId, GridType type) {
        this.text = text;
        this.resourceId = resourceId;
        this.type = type;

    }
}
