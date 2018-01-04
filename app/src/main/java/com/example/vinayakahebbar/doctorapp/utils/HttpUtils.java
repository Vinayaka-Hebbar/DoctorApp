package com.example.vinayakahebbar.doctorapp.utils;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vinayaka Hebbar on 04-01-2018.
 */

public class HttpUtils {
    private static final String baseUrl = "http://learncodes.co.in/doctor/";

    public HttpUtils(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    private String httpUrl;

    public OnLoaded getOnLoaded() {
        return onLoaded;
    }

    public void setOnLoaded(OnLoaded onLoaded) {
        this.onLoaded = onLoaded;
    }

    private OnLoaded onLoaded;
    public void getJsonString(){
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String response = null;
                try {
                    if(!TextUtils.isEmpty(httpUrl)) {
                        URL url = new URL(httpUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        // read the response
                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        response = convertStreamToString(in);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                return response;
            }
        }.execute();
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void getHospitalLocations(){
        httpUrl = "Hospital/locations.json";
        getJsonString();
    }

    private void getDoctors(){
        httpUrl = "Doctors/BTM/btm.json";
        getJsonString();
    }
}
