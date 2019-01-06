package com.example.admin.xinyueapp.entity.WeatherBean;

public class Now {
    //温度
    private int tmp;
    //天气状况
    private String cond_txt;
    //风向
    private String wind_dir;
    //风力
    private int wind_sc;

    public int getTmp(){
        return tmp;
    }
    public void setTmp(int tmp){
        this.tmp = tmp;
    }

    public String getCond_txt(){
        return cond_txt;
    }
    public void setCond_txt(String cond_txt){
        this.cond_txt = cond_txt;
    }

    public String getWind_dir(){
        return wind_dir;
    }
    public void setWind_dir(String wind_dir){
        this.wind_dir = wind_dir;
    }

    public int getWind_sc(){
        return wind_sc;
    }
    public void setWind_sc(int wind_sc){
        this.wind_sc = wind_sc;
    }
}
