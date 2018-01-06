package com.example.vinayakahebbar.doctorapp.utils;

import android.os.AsyncTask;

import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.model.Doctor;
import com.example.vinayakahebbar.doctorapp.model.DoctorLocation;
import com.example.vinayakahebbar.doctorapp.model.Hospital;
import com.example.vinayakahebbar.doctorapp.model.ModelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonIO {
    public JsonIO(String jsonString) {
        this.jsonString = jsonString;
    }

    public void setOnLoaded(OnListLoaded onLoaded) {
        this.onLoaded = onLoaded;
    }

    private OnListLoaded onLoaded;

    private String jsonString;

    public void getHospitalDetailsFromJson(){
        final List<ModelView> locations = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for (int i=0;i<array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("Name");
                        String address = object.getString("Address");
                        double lan = Double.parseDouble(object.getString("Lan"));
                        double lat = Double.parseDouble(object.getString("Lat"));
                        Hospital location = new Hospital(name,address,lat,lan);
                        locations.add(location);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLoaded.Update(locations);
            }
        }.execute();
    }

    public void getDoctorsInfo(){
        final List<ModelView> doctors = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("DoctorName");
                        String address = object.getString("Address");
                        String info = object.getString("Info");
                        String spec = object.getString("Spec");
                        Doctor doctor = new Doctor(name,address);
                        doctor.setInformation(info);
                        doctor.setSpecialization(spec);
                        doctors.add(doctor);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLoaded.Update(doctors);
            }
        }.execute();
    }

    public void getDoctorLocation(){
        final List<ModelView> locations = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("Name");
                        String path = object.getString("Path");
                        DoctorLocation location = new DoctorLocation(name,path);
                        locations.add(location);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLoaded.Update(locations);
            }
        }.execute();
    }
}
