package com.example.vinayakahebbar.doctorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.SelfCareGridAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.model.SelfCareModel;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;

import java.util.ArrayList;
import java.util.List;

public class SelfCareActivity extends AppCompatActivity implements OnLoaded, OnListLoaded {
    private GridView gridView;
    private SelfCareGridAdapter adapter;
    private List<SelfCareModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care);
        list = new ArrayList<>();
        gridView = (GridView)findViewById(R.id.gv_self_care);
        adapter = new SelfCareGridAdapter(this,R.layout.self_care_grid_item,list);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setOnLoaded(this);
        httpUtils.getSelfCareInfo();
        super.onStart();
    }

    @Override
    public void Update(String text) {
        JsonIO jsonIO = new JsonIO(text);
        jsonIO.setOnLoaded(this);
        jsonIO.getSelfCareInfo();
    }

    @Override
    public void UpdateList(List<ModelView> lists) {
        for (ModelView view : lists) {
            list.add((SelfCareModel) view);
        }
        adapter.notifyDataSetChanged();
    }
}
