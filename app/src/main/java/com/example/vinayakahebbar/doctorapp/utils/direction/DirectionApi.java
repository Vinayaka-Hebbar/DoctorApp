package com.example.vinayakahebbar.doctorapp.utils.direction;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.example.vinayakahebbar.doctorapp.utils.volley.VolleySingleton;

/**
 * Created by Vinayaka Hebbar on 05-01-2018.
 */

public class DirectionApi extends Application {
        private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
