package com.example.vinayakahebbar.doctorapp.utils;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.BloodBank;
import com.example.vinayakahebbar.doctorapp.model.BloodBankLocation;
import com.example.vinayakahebbar.doctorapp.model.Doctor;
import com.example.vinayakahebbar.doctorapp.model.DoctorLocation;
import com.example.vinayakahebbar.doctorapp.model.Hospital;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.model.SelfCareModel;

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
                    if(TextUtils.isEmpty(jsonString))return null;
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
                onLoaded.UpdateList(locations);
            }
        }.execute();
    }

    public void getDoctorsInfo(){
        final List<ModelView> doctors = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if(TextUtils.isEmpty(jsonString))return null;
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("DoctorName");
                        String address = object.getString("Address");
                        String info = object.getString("Info");
                        String spec = object.getString("Spec");
                        String imgUrl = HttpUtils.BASE_URL + "Doctors" + object.getString("ImgPath").replace('\\','/');
                        Doctor doctor = new Doctor(name,address);
                        doctor.setImgUrl(imgUrl);
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
                onLoaded.UpdateList(doctors);
            }
        }.execute();
    }

    public void getDoctorLocation(){
        final List<ModelView> locations = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if(TextUtils.isEmpty(jsonString))return null;
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
                onLoaded.UpdateList(locations);
            }
        }.execute();
    }

    public void getSelfCareInfo(){
        final List<ModelView> info = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String topic = object.getString("Topic");
                        String img = HttpUtils.BASE_URL + "SelfCare" + object.getString("Img");
                        String shortDesc = object.getString("ShortDes");
                        String desc = object.getString("Desc");
                        SelfCareModel model = new SelfCareModel(topic,img.replace('\\','/'),shortDesc,desc);
                        info.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLoaded.UpdateList(info);
            }
        }.execute();
    }

    public void getBloodBankInfo(){
        final List<ModelView> info = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if(TextUtils.isEmpty(jsonString))return null;
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for(int i=0;i< array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("Name");
                        String address = object.getString("Address");
                        String phoneNo = object.getString("PhoneNo");
                        String timing = object.getString("Time");
                        BloodBank model = new BloodBank(name,phoneNo,address,timing);
                        info.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLoaded.UpdateList(info);
            }
        }.execute();
    }

    public void getBloodBankLocations(){
        final List<ModelView> locations = new ArrayList<>();
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if(TextUtils.isEmpty(jsonString))return null;
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray array = jsonObject.getJSONArray("Items");
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("Name");
                        String path = object.getString("Path");
                        BloodBankLocation location = new BloodBankLocation(name,path);
                        locations.add(location);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLoaded.UpdateList(locations);
            }
        }.execute();
    }


}
