package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.BloodBank;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class BloodBankListAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<BloodBank> objects;
    private LayoutInflater inflater;

    public BloodBankListAdapter(Context context, int resource, List<BloodBank> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BloodBank getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(resource,null);
            BloodBank bank = getItem(position);
            TextView textViewName = (TextView)convertView.findViewById(R.id.tv_list_bank_name);
            TextView textViewAddress = (TextView)convertView.findViewById(R.id.tv_list_bank_address);
            TextView textViewPhone = (TextView)convertView.findViewById(R.id.tv_list_bank_phone);
            TextView textViewTime = (TextView)convertView.findViewById(R.id.tv_list_bank_time);
            textViewName.setText(bank.getName());
            textViewAddress.setText(bank.getAddress());
            textViewPhone.setText(bank.getPhoneNo());
            textViewTime.setText(bank.getTiming());
        }
        return convertView;
    }
}
