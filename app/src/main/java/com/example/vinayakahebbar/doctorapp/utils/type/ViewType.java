package com.example.vinayakahebbar.doctorapp.utils.type;

/**
 * Created by Vinayaka Hebbar on 07-01-2018.
 */

public class ViewType {
    public static final int BLOOD_BANK = 4;
    public static final int SELF_CARE = 5;

    public ViewType(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }
}
