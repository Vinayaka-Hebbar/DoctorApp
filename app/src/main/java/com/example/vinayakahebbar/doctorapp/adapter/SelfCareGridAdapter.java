package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
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
import com.example.vinayakahebbar.doctorapp.interfaces.AnimationItemClickListener;
import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;
import com.example.vinayakahebbar.doctorapp.model.SelfCareModel;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.ImageHelper;
import com.example.vinayakahebbar.doctorapp.views.ImageLoaderView;

import java.util.List;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class SelfCareGridAdapter extends BaseAdapter{
    private Context context;
    private int resource;
    private List<SelfCareModel> objects;
    private LayoutInflater inflater;

    public void setItemClickListener(AnimationItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private AnimationItemClickListener itemClickListener;

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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SelfCareModel item = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }
            final LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout_grid_self_care);

            TextView textViewTopic = (TextView) layout.findViewById(R.id.tv_grid_self_care_topic);
            final TextView textViewShortDesc = (TextView) layout.findViewById(R.id.tv_grid_self_care_short_desc);
            final ImageLoaderView imageLoaderView = (ImageLoaderView) layout.findViewById(R.id.img_grid_self_care);
            imageLoaderView.loadImageWithUri(item.getImgUri());
            ViewCompat.setTransitionName(imageLoaderView.getImageView(),item.getTopic());
            ViewCompat.setTransitionName(textViewShortDesc,item.getShortDes());
            textViewTopic.setText(item.getTopic());
            textViewShortDesc.setText(item.getShortDes());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.toArray();
                    item.setIndex(5);
                    itemClickListener.OnAnimationItemClick(position, item, new View[]{imageLoaderView.getImageView(),textViewShortDesc});
                }
            });

        return convertView;
    }
}
