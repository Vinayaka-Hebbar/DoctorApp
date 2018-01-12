package com.example.vinayakahebbar.doctorapp.utils.type;

import com.example.vinayakahebbar.doctorapp.interfaces.ObjectListener;

/**
 * Created by Vinayaka Hebbar on 11-01-2018.
 */

public class ObjectType implements ObjectListener {
    private int intValue;
    private String stringValue;
    private Object objectValue;
    @Override
    public int getInt() {
        return intValue;
    }

    @Override
    public String getString() {
        return stringValue;
    }

    @Override
    public Object getValue() {
        return objectValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }

    public ObjectType(int intValue) {
        this.intValue = intValue;
    }

    public ObjectType(String stringValue) {
        this.stringValue = stringValue;
    }

    public ObjectType(Object objectValue) {
        this.objectValue = objectValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
