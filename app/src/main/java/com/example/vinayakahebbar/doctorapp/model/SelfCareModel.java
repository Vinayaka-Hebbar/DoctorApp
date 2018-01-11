package com.example.vinayakahebbar.doctorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class SelfCareModel extends ModelView {
    private String topic;
    private String imgUri;
    private String shortDes;
    private String desc;

    public SelfCareModel() {
    }

    public SelfCareModel(String topic, String imgUri, String shortDes, String desc) {
        this.topic = topic;
        this.imgUri = imgUri;
        this.shortDes = shortDes;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getImgUri() {
        return imgUri;
    }

    public String getShortDes() {
        return shortDes;
    }

    public String getDesc() {
        return desc;
    }

    public void toArray(){
        setParams(new String[]{topic,imgUri,shortDes,desc});
    }
}
