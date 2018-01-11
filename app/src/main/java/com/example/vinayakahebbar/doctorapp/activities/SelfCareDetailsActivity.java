package com.example.vinayakahebbar.doctorapp.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.model.SelfCareModel;
import com.example.vinayakahebbar.doctorapp.utils.ImageHelper;

public class SelfCareDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care_details);
        final Bundle extras = getIntent().getExtras();
        ModelView view =  extras.getParcelable(SelfCareActivity.IMAGE_KEY);
        final ImageView imageView = (ImageView)findViewById(R.id.img_self_detail);
        final TextView textView = (TextView)findViewById(R.id.tv_self_desc);
        textView.setText(view.getParams()[3]);
        ImageHelper helper = new ImageHelper(getApplicationContext());
        helper.setOnImageLoaded(new OnImageLoaded() {
            @Override
            public void LoadImage(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String imageTransitionName = extras.getString(SelfCareActivity.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
                    String textTransitionName = extras.getString(SelfCareActivity.EXTRA_ANIMATION_DESC);
                    if (!TextUtils.isEmpty(imageTransitionName))
                        imageView.setTransitionName(imageTransitionName);
                    textView.setTransitionName(textTransitionName);
                }
            }
        });
        helper.loadBitmapFromURL(view.getParams()[1]);



    }
}
