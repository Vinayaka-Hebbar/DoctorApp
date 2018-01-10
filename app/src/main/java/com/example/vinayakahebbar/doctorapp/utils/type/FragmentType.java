package com.example.vinayakahebbar.doctorapp.utils.type;

/**
 * Created by Vinayaka Hebbar on 04-01-2018.
 */

public class  FragmentType extends ViewType {
    public static final int HOME = 1;
    public static final int PROFILE = 6;
    public static final int HOSPITAL=2;
    public static final int DOC=3;
    public static final int DOC_LIST=8;
    public static final int HELP=7;
    public static final int EMERGENCY = 9;

    private int type;

    public FragmentType(int type) {
        super(type);
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }
}
