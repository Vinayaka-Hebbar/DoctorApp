package com.example.vinayakahebbar.doctorapp.utils;

import android.os.AsyncTask;

import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.Doctor;
import com.example.vinayakahebbar.doctorapp.model.HospitalLocation;
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
                        HospitalLocation location = new HospitalLocation(name,address);
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
                        Doctor doctor = new Doctor(name,address);
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
}
