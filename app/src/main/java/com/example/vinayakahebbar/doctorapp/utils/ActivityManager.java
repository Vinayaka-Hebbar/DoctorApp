package com.example.vinayakahebbar.doctorapp.utils;

import android.content.Context;
import android.content.Intent;

import com.example.vinayakahebbar.doctorapp.activities.BloodBankActivity;
import com.example.vinayakahebbar.doctorapp.activities.SelfCareActivity;
import com.example.vinayakahebbar.doctorapp.utils.type.ViewType;

/**
 * Created by Vinayaka Hebbar on 08-01-2018.
 */

public class ActivityManager {
    private Context context;

    public ActivityManager(Context context) {
        this.context = context;
    }

    public void startActivity(int type){
        switch (type){
            case ViewType.BLOOD_BANK:
                context.startActivity(new Intent(context, BloodBankActivity.class));
                break;
            case ViewType.SELF_CARE:
                context.startActivity(new Intent(context, SelfCareActivity.class));
                break;
        }
    }
}
