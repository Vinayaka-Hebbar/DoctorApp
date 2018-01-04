package com.example.vinayakahebbar.doctorapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.Doctor;

import java.util.List;

/**
 * Created by US76 on 04-01-2018.
 */

public class DoctorListAdaptor extends BaseAdapter {
    public DoctorListAdaptor(Context context, int resource,List<Doctor> list) {
        this.list = list;
        this.context = context;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    private List<Doctor> list;
    private Context context;
    private int resource;
    private LayoutInflater inflater;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Doctor getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);
        if(convertView == null)
            convertView = inflater.inflate(resource,null);
        TextView textViewName = (TextView)convertView.findViewById(R.id.tv_doctor_name);
        textViewName.setText(doctor.getName());

        TextView textViewAddress = (TextView)convertView.findViewById(R.id.tv_doctor_address);
        textViewAddress.setText(doctor.getAddress());
        return convertView;
    }
}
