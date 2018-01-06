package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;

/**
 * Created by Vinayaka Hebbar on 05-01-2018.
 */

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    public ViewPageAdapter(Context context, int[] resources) {
        this.context = context;
        this.resources = resources;
        inflater = LayoutInflater.from(context);
    }

    int[] resources;
    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int res = resources[position];
        View view = inflater.inflate(R.layout.main_view_pager,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.img_view_pager);
        imageView.setImageResource(res);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}
