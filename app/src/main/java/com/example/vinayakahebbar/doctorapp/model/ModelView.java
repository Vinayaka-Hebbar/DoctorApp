package com.example.vinayakahebbar.doctorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinayaka Hebbar on 04-01-2018.
 */

public class ModelView implements Parcelable {
    public void setIndex(int index) {
        this.index = index;
    }

    private int index;
    private boolean isValue;



    public ModelView(Object object) {
        this.object = object;
    }

    private Object object;

    private String[] params;

    public ModelView(String[] params) {
        this.params = params;
    }

    public ModelView(int index) {
        this.index = index;
    }

    public ModelView(){
    }

    protected ModelView(Parcel in) {
        index = in.readInt();
        int len = in.readInt();
        params = new String[len];
        for (int i=0;i<len;i++) {
            params[i] = in.readString();
        }
    }

    public static final Creator<ModelView> CREATOR = new Creator<ModelView>() {
        @Override
        public ModelView createFromParcel(Parcel in) {
            return new ModelView(in);
        }

        @Override
        public ModelView[] newArray(int size) {
            return new ModelView[size];
        }
    };

    @Override
    public String toString() {
        return object.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeInt(params.length);
        for (String val :
                params) {
            dest.writeString(val);
        }
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public boolean isValue() {
        return isValue;
    }

    public void setValue(boolean value) {
        isValue = value;
    }
}
