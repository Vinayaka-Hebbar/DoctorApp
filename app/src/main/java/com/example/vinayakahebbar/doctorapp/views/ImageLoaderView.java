package com.example.vinayakahebbar.doctorapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.toolbox.ImageLoader;
import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;
import com.example.vinayakahebbar.doctorapp.utils.ImageHelper;

/**
 * Created by Vinayaka Hebbar on 10-01-2018.
 */

public class ImageLoaderView extends LinearLayout implements OnImageLoaded {
    private ProgressBar progressBar;
    private int position;
    private boolean isLoaded;
    private ImageHelper helper;

    public ImageView getImageView() {
        return imageView;
    }

    private ImageView imageView;
    private int height;

    public ImageLoaderView(Context context,int height) {
        super(context);
        setOrientation(VERTICAL);
        progressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleSmall);
        progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        progressBar.setIndeterminate(true);
        addView(progressBar);
        imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(imageView);
    }

    public ImageLoaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageLoaderView, 0, 0);
        try {
            height = ta.getInteger(R.styleable.ImageLoaderView_img_height, 100);
        } finally {
            ta.recycle();
        }
        setOrientation(VERTICAL);
        progressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleSmall);
        progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        progressBar.setIndeterminate(true);
        addView(progressBar);
        imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(imageView);
    }

    public void loadImageWithUri(String url){
        imageView.setVisibility(GONE);
        helper = new ImageHelper(getContext());
        helper.setOnImageLoaded(this);
        progressBar.setVisibility(VISIBLE);
        helper.loadBitmapFromURL(url);
    }

    @Override
    public void LoadImage(Bitmap bitmap) {
        isLoaded = true;
        progressBar.setVisibility(GONE);
        imageView.setVisibility(VISIBLE);
        imageView.setImageBitmap(bitmap);
    }
}
