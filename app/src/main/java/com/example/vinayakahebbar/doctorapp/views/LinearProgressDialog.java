package com.example.vinayakahebbar.doctorapp.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;

public class LinearProgressDialog extends Dialog {

    private LinearLayout layout;
    private TextView textViewTitle;

    public LinearProgressDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = new LinearProgressBar(getContext());
        view.setPadding(10,10,10,10);

        //Title Bar
        textViewTitle = new TextView(context);
        textViewTitle.setLayoutParams(new ViewGroup.LayoutParams(375,65));
        textViewTitle.setTextSize(16);
        textViewTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        textViewTitle.setPadding(10,10,10,10);
        textViewTitle.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        //Main Layout
        layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(textViewTitle);
        layout.addView(view);
        setContentView(layout);
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        textViewTitle.setText(title);
    }
}
