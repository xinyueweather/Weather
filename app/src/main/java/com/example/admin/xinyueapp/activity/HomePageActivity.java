package com.example.admin.xinyueapp.activity;

import android.Manifest;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.adapter.WeatherAdapter;

import com.example.admin.xinyueapp.entity.Alist;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;

import interfaces.heweather.com.interfacesmodule.bean.air.Air;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class HomePageActivity extends StartActivity {


    private RecyclerView mWeatherRv;

    ArrayList<String> nowResults = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button addCity = (Button) findViewById(R.id.addCity);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AddLocationActivity.class);
                startActivity(intent);
            }
        });
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        mWeatherRv = (RecyclerView) findViewById(R.id.recycler_view2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mWeatherRv.setLayoutManager(manager);

        HeConfig.init(this.getString(R.string.id), this.getString(R.string.key));
        HeConfig.switchToFreeServerNode();
        getWeather();

    }

    private boolean getWeather() {
        HeWeather.getWeather(this, "武汉", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onSuccess(List<Weather> list) {
                List<Object> arrays_obj = new ArrayList<>();
                Alist.MyDataList.NowList nl = new Alist.MyDataList.NowList();
                nl.setNowCondTxt(list.get(0).getNow().getCond_txt());
                nl.setNowTem(list.get(0).getNow().getTmp() + "℃");
                nl.setTolTem(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃ / " + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                arrays_obj.add(nl);
                //Log.i("Log", "ONSU" + nl.getCurTem());

                Alist.MyDataList.HourlyList hlnow = new Alist.MyDataList.HourlyList();
                hlnow.sethTime("现在");
                int image = getResources().getIdentifier("w" + list.get(0).getNow().getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hlnow.sethCondIma(image);
                hlnow.sethTmp(list.get(0).getNow().getTmp() + "℃");
                arrays_obj.add(hlnow);
                for (int i = 0; i < list.get(0).getHourly().size(); i++) {
                    Alist.MyDataList.HourlyList hl = new Alist.MyDataList.HourlyList();
                    hl.sethTime(list.get(0).getHourly().get(i).getTime().substring(11));
                    int imageId = getResources().getIdentifier("w" + list.get(0).getHourly().get(i).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                    hl.sethCondIma(imageId);

                    hl.sethTmp(list.get(0).getHourly().get(i).getTmp() + "℃");
                    arrays_obj.add(hl);
                }
                for (int i = 0; i < list.get(0).getDaily_forecast().size(); i++) {
                    Alist.MyDataList.DaysList dl = new Alist.MyDataList.DaysList();
                    dl.setDate(list.get(0).getDaily_forecast().get(i).getDate());
                    dl.setDayTem(list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃ / " + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    int imageId = getResources().getIdentifier("w" + list.get(0).getDaily_forecast().get(i).getCond_code_d(), "drawable", "com.example.admin.xinyueapp");
                    dl.setDCondIma(imageId);
                    arrays_obj.add(dl);
                }
                Alist.MyDataList.ComfList cl = new Alist.MyDataList.ComfList();
                cl.setCFi(list.get(0).getNow().getFl() + "℃");
                cl.setCHum(list.get(0).getNow().getHum());
                cl.setCUv(list.get(0).getDaily_forecast().get(0).getUv_index());
                arrays_obj.add(cl);


                WeatherAdapter addapter = new WeatherAdapter();
                mWeatherRv.setAdapter(addapter);
                addapter.setData(arrays_obj);
                Log.i("Log", "ONSU" + arrays_obj);
                //addapter.setData(new AlistModel().getData());*/


                //list.get(0).getLifestyle().get(0).getType();
            }
        });
        return true;
    }


}
