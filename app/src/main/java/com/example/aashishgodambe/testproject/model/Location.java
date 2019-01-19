package com.example.aashishgodambe.testproject.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by aashishgodambe on 1/14/19.
 */
@Entity
public class Location {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String street;
    private String city;
    private String style;
    private String name;
    private String lat;
    private String lng;
    private String state;
    private String address;
    private String details;

    @Ignore
    public Location(int id,String street, String city, String style, String name,
                    String lat, String lng, String state, String address, String details) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.style = style;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.state = state;
        this.address = address;
        this.details = details;
    }

    public Location(String street, String city, String style, String name,
                    String lat, String lng, String state, String address, String details) {
        this.street = street;
        this.city = city;
        this.style = style;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.state = state;
        this.address = address;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", style='" + style + '\'' +
                ", name='" + name + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getStyle() {
        return style;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public String getDetails() {
        return details;
    }
}
