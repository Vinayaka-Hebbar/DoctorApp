package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.DoctorLocation;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 06-01-2018.
 */

public class DoctorsLocationAdapter extends BaseAdapter {
    public DoctorsLocationAdapter(List<DoctorLocation> objects, Context context, int resource) {
        this.objects = objects;
        this.context = context;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    private List<DoctorLocation> objects;
    private Context context;
    private int resource;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public DoctorLocation getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = inflater.inflate(resource,null);
        DoctorLocation location = getItem(position);
        TextView textViewName = (TextView)convertView.findViewById(R.id.tv_location_name);
        textViewName.setText(location.getName());
        return convertView;
    }
}
