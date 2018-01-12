package com.example.vinayakahebbar.doctorapp.interfaces;

/**
 * Created by Vinayaka Hebbar on 11-01-2018.
 */

public interface NetworkListener {
    public void onLoaded(String response);
    public void onNetworkError(String error);

}
