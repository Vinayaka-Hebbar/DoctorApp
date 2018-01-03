package com.example.vinayakahebbar.doctorapp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class KeyboardHelper {
    public void hideKeyboard(Context context,View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
