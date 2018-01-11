package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.EmergencyContact;

import java.util.List;

public class EmergencyContactAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<EmergencyContact>list;
    private LayoutInflater inflater;

    public EmergencyContactAdapter(Context context,int resource, List<EmergencyContact> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EmergencyContact getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmergencyContact contacts = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_emgcy);
        textView.setText(contacts.getName());
        TextView textView1 = (TextView) convertView.findViewById(R.id.tv_emgcy_no);
        textView1.setText(String.valueOf(contacts.getPhNo()));

        return convertView;
    }
}
