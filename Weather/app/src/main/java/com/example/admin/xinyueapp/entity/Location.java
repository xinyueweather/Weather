package com.example.admin.xinyueapp.entity;

import java.util.HashMap;

public class Location {
    private String mLocation;
    private String mAdmin;
    private String mCid;
    public Location(String location , String city , String province , String cid){
        mLocation = location;
        if(province.equals(" "))
            mAdmin=city;
        else
            mAdmin = province + " , " + city;
        mCid = cid;
    }

    public String getLocation(){
        return mLocation;
    }

    public String getCid() {
        return mCid;
    }

    public String getAdmin() {
        return mAdmin;
    }
}
