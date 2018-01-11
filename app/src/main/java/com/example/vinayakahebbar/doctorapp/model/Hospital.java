package com.example.vinayakahebbar.doctorapp.model;


public class Hospital extends ModelView {

    private double lat;
    private double lan;
    private String name;
    private String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public double getLat() {
        return lat;
    }

    public double getLan() {
        return lan;
    }


    public Hospital(String name, String address) {
        this.name = name;
        this.address = address;
        setValue(true);
    }

    /**
     *
     * @param name
     * @param address
     * @param lat
     * @param lan
     */
    public Hospital(String name, String address, double lat, double lan) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lan = lan;
        setValue(true);
    }
}
