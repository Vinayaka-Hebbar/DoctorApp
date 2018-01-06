package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.Hospital;
import com.example.vinayakahebbar.doctorapp.model.ModelView;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 05-01-2018.
 */

public class HospitalListAdapter extends BaseAdapter {
    private List<Hospital> objects;
    private Context context;
    private int resource;
    private LayoutInflater inflater;

    public HospitalListAdapter(Context context, int resource,List<Hospital> objects) {
        this.objects = objects;
        this.context = context;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Hospital getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(resource,null);
        Hospital hospital = getItem(position);
        TextView textViewName = (TextView)convertView.findViewById(R.id.tv_hospital_list);
        textViewName.setText(hospital.getName());
        return convertView;
    }
}
