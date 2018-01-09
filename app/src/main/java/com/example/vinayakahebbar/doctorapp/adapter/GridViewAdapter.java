package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.model.GridModel;

import java.util.List;
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<GridModel> list;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, int resource, List<GridModel> list) {
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
    public GridModel getItem(int position) {
     return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(resource,null);
        }
        GridModel gridModel=getItem(position);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.img_grid);
        imageView.setImageResource(gridModel.getResourceId());
        TextView textView=(TextView)convertView.findViewById(R.id.tv_grid);
        textView.setText(gridModel.getText());
        return convertView;
    }
}
