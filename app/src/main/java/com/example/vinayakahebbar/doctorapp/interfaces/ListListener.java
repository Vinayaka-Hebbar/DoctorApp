package com.example.vinayakahebbar.doctorapp.interfaces;

import com.example.vinayakahebbar.doctorapp.model.ModelView;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 05-01-2018.
 */

public interface ListListener {
    public void updateList(List<ModelView> list,String[] params);
}
