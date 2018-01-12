package com.example.vinayakahebbar.doctorapp.utils;

import android.os.AsyncTask;

import com.example.vinayakahebbar.doctorapp.interfaces.NetworkListener;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class HttpUtils {
    public static final String BASE_URL = "http://learncodes.co.in/doctor/";


    public HttpUtils() {
    }

    public HttpUtils(String[] param) {
        this.param = param;
    }

    public String[] getParam() {
        return param;
    }

    private String[] param;
    private String httpUrl;


    public HttpUtils setOnLoaded(NetworkListener networkListener) {
        this.networkListener = networkListener;
        return this;
    }

    private NetworkListener networkListener;
    public void getJsonString() {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String response = null;
                try {

                    URL url = new URL(BASE_URL + httpUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response = convertStreamToString(in);

                } catch (UnknownHostException e) {
                    networkListener.onNetworkError("No Network");
                    cancel(true);
                }catch (Exception e){
                    e.printStackTrace();;
                }
                return response;
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onPostExecute(String s) {
                networkListener.onLoaded(s);
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

    public void getSelfCareInfo(){
        httpUrl = "SelfCare/topics.json";
        getJsonString();
    }

    public void getBloodBankInfo(){
        httpUrl = "BloodBank/index.php?location=" + param[0].replace(" ","%20");
        getJsonString();
    }

    public void getBloodBankLocations() {
        httpUrl = "BloodBank/locations.json";
        getJsonString();
    }

    public void sendJsonObject(final JSONObject object){
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String response = null;
                try {
                    URL url = new URL(BASE_URL + httpUrl);
                    String message = object.toString();
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 15000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();
                    InputStream is = conn.getInputStream();
                    response = convertStreamToString(is);

                } catch (UnknownHostException e) {
                    networkListener.onNetworkError("No Network");
                    cancel(true);
                }catch (Exception e){
                    e.printStackTrace();;
                }
                return response;
            }

            @Override
            protected void onPreExecute() {
                httpUrl = param[0] + "/" + param[1];
            }

            @Override
            protected void onPostExecute(String s) {
                networkListener.onLoaded(s);
            }
        }.execute();
    }

    public void sendJsonObject(final String message){
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String response = null;
                try {
                    URL url = new URL(BASE_URL + httpUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 15000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();
                    InputStream is = conn.getInputStream();
                    response = convertStreamToString(is);

                } catch (UnknownHostException e) {
                    networkListener.onNetworkError("No Network");
                    cancel(true);
                }catch (Exception e){
                    e.printStackTrace();;
                }
                return response;
            }

            @Override
            protected void onPreExecute() {
                httpUrl = param[0] + "/" + param[1];
            }

            @Override
            protected void onPostExecute(String s) {
                networkListener.onLoaded(s);
            }
        }.execute();
    }

    public NetworkListener getNetworkListener() {
        return networkListener;
    }
}
