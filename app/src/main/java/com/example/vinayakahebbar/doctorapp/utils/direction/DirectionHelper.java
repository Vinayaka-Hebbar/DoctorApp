package com.example.vinayakahebbar.doctorapp.utils.direction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vinayaka Hebbar on 05-01-2018.
 */

public class DirectionHelper {
    private static final  String DIRECTION_API = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    public static  final  String API_KEY = "AIzaSyCmUAGVf0wwfGMPIY9wUfjfymlwSnTJqoo";
    public static final int SOCKET_TIMEOUT = 5000;
    public static String getUrl(LatLng orgLatLng,LatLng destLatLng) {
        return DirectionHelper.DIRECTION_API + orgLatLng.latitude + "," + orgLatLng.longitude
                + "&destination=" + destLatLng.latitude + "," + destLatLng.longitude + "&key=" + API_KEY;
    }
}
