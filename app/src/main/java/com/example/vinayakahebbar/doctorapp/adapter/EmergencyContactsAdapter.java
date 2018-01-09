package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.EmergencyContacts;

import java.util.List;

public class EmergencyContactsAdapter extends BaseAdapter {
    private Context context;
    private int res;
    private List<EmergencyContacts>list;
    private LayoutInflater inflater;

    public EmergencyContactsAdapter(Context context, int res, List<EmergencyContacts> list) {
        this.context = context;
        this.res = res;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EmergencyContacts getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView=inflater.inflate(res,null);
        EmergencyContacts contacts=getItem(position);
        TextView textView=(TextView)convertView.findViewById(R.id.tv_emgcy);
        textView.setText(contacts.getName());
        TextView textView1=(TextView)convertView.findViewById(R.id.tv_emgcy_no);
        textView1.setText(String.valueOf(contacts.getPhNo()));
        return convertView;
    }
}
