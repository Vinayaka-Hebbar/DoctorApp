package com.example.vinayakahebbar.doctorapp.activities;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.SelfCareGridAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.AnimationItemClickListener;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.model.SelfCareModel;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;

import java.util.ArrayList;
import java.util.List;

public class SelfCareActivity extends AppCompatActivity implements OnLoaded, OnListLoaded, AnimationItemClickListener {
    private GridView gridView;
    private SelfCareGridAdapter adapter;
    private List<SelfCareModel> list;
    public static final String IMAGE_KEY = "img_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animation_image";
    public static final String EXTRA_ANIMATION_DESC = "animation_desc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care);
        list = new ArrayList<>();
        gridView = (GridView)findViewById(R.id.gv_self_care);
        adapter = new SelfCareGridAdapter(this,R.layout.self_care_grid_item,list);
        adapter.setItemClickListener(this);
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
        if (list.size() != 0) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
        for (ModelView view : lists) {
            list.add((SelfCareModel) view);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnAnimationItemClick(int pos, ModelView view, View[] views) {
        Intent intent = new Intent(this,SelfCareDetailsActivity.class);
        intent.putExtra(IMAGE_KEY,view);
        String imageAnimationName = ViewCompat.getTransitionName(views[0]);
        String textAnimationName = ViewCompat.getTransitionName(views[1]);
        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, imageAnimationName);
        intent.putExtra(EXTRA_ANIMATION_DESC,textAnimationName);
        Pair<View, String> pair1 = Pair.create(views[0], imageAnimationName);
        Pair<View, String> pair2 = Pair.create(views[1], textAnimationName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pair1,pair2);
        startActivity(intent,options.toBundle());
    }
}
