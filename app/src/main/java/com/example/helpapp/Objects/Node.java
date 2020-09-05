package com.example.helpapp.Objects;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class Node {

    private int id;
    private String companyName;
    private double latitude;
    private double longitude;

    public Node() {
    }

    public Node(int id, String company_name, double latitude, double longitude) {
        this.id = id;
        this.companyName = company_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
