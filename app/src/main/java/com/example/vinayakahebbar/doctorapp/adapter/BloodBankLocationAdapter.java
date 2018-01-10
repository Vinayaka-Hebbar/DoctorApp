package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.BloodBankLocation;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class BloodBankLocationAdapter extends BaseAdapter {
    private Context context;
    private List<BloodBankLocation> objects;

    public BloodBankLocationAdapter(Context context, List<BloodBankLocation> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(context, android.R.layout.simple_spinner_dropdown_item, null);
            TextView textView = (TextView)convertView;
            textView.setPadding(10,10,10,10);
            textView.setText(getItem(position).toString());
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public BloodBankLocation getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LinearLayout layout = new LinearLayout(context);
            layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            BloodBankLocation location = getItem(position);
            TextView textView = new TextView(context);
            textView.setTextSize(18);
            textView.setPadding(5,5,5,5);
            textView.setText(location.getName());
            layout.addView(textView);
            convertView = layout;
        }
        return convertView;
    }
}
