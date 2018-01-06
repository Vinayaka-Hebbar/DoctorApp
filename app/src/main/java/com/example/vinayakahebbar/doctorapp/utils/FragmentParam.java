package com.example.vinayakahebbar.doctorapp.utils;

/**
 * Created by Vinayaka Hebbar on 06-01-2018.
 */

public class FragmentParam {
    /**
     *
     * @param args Constructor
     */
    public FragmentParam(String[] args) {
        this.args = args;
        instance = this;
    }
    public static FragmentParam instance;
    public String[] args;
}
