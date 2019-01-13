package com.example.admin.xinyueapp.entity;

import android.util.Log;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class AlistModel {
    public AlistModel() {
    }

    public Alist getData() {
        Alist alist = new Alist();
        alist.setData(getAllData());
        return alist;
    }

    private List<Object> sortData(Alist alist) {
        List<Alist.MyDataList> arrays = alist.getData();
        List<Object> arrays_obj = new ArrayList<>();

        for (Alist.MyDataList array : arrays) {
            List<Alist.MyDataList.NowList> summ = array.getNowList();
            List<Alist.MyDataList.DaysList> days = array.getDaysList();
          /*  if (summ != null && summ.size() > 0) {
                for (Alist.MyDataList.NowList sum : summ) {
                    arrays_obj.add(sum);

                }
            }
            if (days != null && days.size() > 0) {
                for (Alist.MyDataList.DaysList day : days) {
                    arrays_obj.add(day);
                }
            }*/
          arrays_obj.add(summ);
          arrays_obj.add(days);
        }
        return arrays_obj;
    }

    private List<Alist.MyDataList.NowList> getNowData(int size) {
        List<Alist.MyDataList.NowList> summ = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Alist.MyDataList.NowList mySumm = new Alist.MyDataList.NowList();
            int a = i + 1;
            mySumm.setNowCondTxt("阴" + a);
            mySumm.setNowTem("1" + a + "℃");
            mySumm.setTolTem("10/15℃" + a);
            summ.add(mySumm);
        }
        return summ;
    }

    private List<Alist.MyDataList.DaysList> getDaysData(int size) {
        List<Alist.MyDataList.DaysList> day = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Alist.MyDataList.DaysList myDay = new Alist.MyDataList.DaysList();
            int a = i + 1;
            myDay.setDate("2018.12.11 " + a);
          //  myDay.setDaySta(R.drawable.orange);
            myDay.setDayTem("10/15℃" + a);
            day.add(myDay);
        }
        return day;
    }

    private List<Alist.MyDataList> getAllData() {
        List<Alist.MyDataList> data = new ArrayList<>();
        Alist.MyDataList myData = new Alist.MyDataList();
        myData.setNowList(getNowData(1));
        data.add(myData);
       for (int i = 0; i <2; i++) {
            myData.setDaysList(getDaysData(i + 1));
            data.add(myData);
        }
        return data;
    }
}