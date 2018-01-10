package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;
import com.example.vinayakahebbar.doctorapp.model.Doctor;
import com.example.vinayakahebbar.doctorapp.utils.ImageHelper;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 06-01-2018.
 */

public class DoctorListAdapter extends BaseAdapter {
    public DoctorListAdapter(Context context, int resource, List<Doctor> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    private Context context;
    private int resource;
    private List<Doctor> objects;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Doctor getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(resource, null);
            Doctor doctor = (Doctor)getItem(position);
            ImageHelper helper = new ImageHelper();

            TextView textViewName = (TextView)convertView.findViewById(R.id.tv_doc_name);
            final ImageView imageView=(ImageView)convertView.findViewById((R.id.img_doc_list));
            helper.setOnImageLoaded(new OnImageLoaded() {
                @Override
                public void LoadImage(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });
            helper.loadBitmapFromURL(doctor.getImgUrl());

            textViewName.setText(doctor.getName());
        }
        return convertView;
    }
}
