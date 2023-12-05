package com.example.firstapp;


public class LocationSchema {

    private String location_id ;
    private String name;

    private double latitude;

    private double longitude;

    public LocationSchema(String location_id, String name, double latitude, double longitude) {
        this.location_id = location_id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
