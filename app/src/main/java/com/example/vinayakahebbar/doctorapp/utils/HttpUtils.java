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


    public HttpUtils() {
    }

    public HttpUtils(String[] param) {
        this.param = param;
    }

    private String[] param;
    private String httpUrl;

    public OnLoaded getOnLoaded() {
        return onLoaded;
    }

    public void setOnLoaded(OnLoaded onLoaded) {
        this.onLoaded = onLoaded;
    }

    private OnLoaded onLoaded;
    public void getJsonString() {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String response = null;
                try {

                    URL url = new URL(baseUrl + httpUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response = convertStreamToString(in);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                onLoaded.Update(s);
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

    public void getHospitalLocations(){
        httpUrl = "Hospital/index.php?location=" + param[0].replace(" ","%20");
        getJsonString();
    }

    public void getDoctors(){
        httpUrl = "Doctors/"+ param[0] +"/"+param[1]+".json";
        getJsonString();
    }

    public void getDoctorLocation(){
        httpUrl = "Doctors/locations.json";
        getJsonString();
    }
}
