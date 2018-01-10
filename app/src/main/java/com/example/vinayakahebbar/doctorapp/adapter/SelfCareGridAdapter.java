package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;
import com.example.vinayakahebbar.doctorapp.model.SelfCareModel;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.ImageHelper;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class SelfCareGridAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<SelfCareModel> objects;
    private LayoutInflater inflater;

    public SelfCareGridAdapter(Context context, int resource, List<SelfCareModel> objects) {
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
    public SelfCareModel getItem(int position) {
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
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout_grid_self_care);
            SelfCareModel item = getItem(position);
            ImageHelper helper = new ImageHelper();
            TextView textViewTopic = (TextView) layout.findViewById(R.id.tv_grid_self_care_topic);
            TextView textViewShortDesc = new TextView(context);
            textViewShortDesc.setEllipsize(TextUtils.TruncateAt.END);
            textViewShortDesc.setMaxLines(3);
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pb_grid_self_care);
            final ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,250));
            helper.setOnImageLoaded(new OnImageLoaded() {
                @Override
                public void LoadImage(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                }
            });
            helper.loadBitmapFromURL(item.getImgUri());
            textViewTopic.setText(item.getTopic());
            textViewShortDesc.setText(item.getShortDes());
            layout.addView(imageView);
            layout.addView(textViewShortDesc);
        }
        return convertView;
    }
}
