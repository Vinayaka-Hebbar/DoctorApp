package com.example.vinayakahebbar.doctorapp.interfaces;

import com.example.vinayakahebbar.doctorapp.model.ModelView;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 11-01-2018.
 */

public interface JsonListener {
    public void onParsed(List<ModelView> lists);
    public void onParseError(String error);
}
