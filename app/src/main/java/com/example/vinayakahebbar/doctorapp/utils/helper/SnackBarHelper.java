package com.example.vinayakahebbar.doctorapp.utils.helper;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.vinayakahebbar.doctorapp.R;

/**
 * Created by Vinayaka Hebbar on 07-01-2018.
 */

public class SnackBarHelper {
    private View view;
    private String message;
    private int duration;
    private Snackbar snackbar;

    public SnackBarHelper(View view, String message, int duration) {
        this.view = view;
        this.message = message;
        this.duration = duration;
    }

    public void showError(){
        snackbar = Snackbar.make(view,message,duration);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.red_light));
        snackbar.show();
    }
}
