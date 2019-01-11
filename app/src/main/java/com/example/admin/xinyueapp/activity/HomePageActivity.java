package com.example.admin.xinyueapp.activity;

import android.Manifest;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.adapter.WeatherAdapter;

import com.example.admin.xinyueapp.entity.Alist;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

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

    /*private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            boolean weather=false;
            boolean air=false;
            List<Object> arrays_obj = new ArrayList<>();

            if(msg.what==2){
                ArrayList<String> record = msg.getData().getStringArrayList("air");
                String pm25=record.get(0);
                Log.i("Log","Onairpm  "+pm25);
                air=true;
            }
            if(msg.what==1){
                ArrayList aa = msg.getData().getParcelableArrayList("aa");
                String record =  new Gson().toJson(aa.get(0));
             try{
               JSONArray oo = new JSONArray(record);
               Log.i("Log","aa.get(0) ? "+ oo);
               for(int i=0;i<oo.length();i++){
                   JSONObject jso = oo.getJSONObject(i);
                   String now = jso.getString("nowCondTxt");
                   Log.i("Log","aa "+now);
               }
             }
               catch (JSONException e){
                 e.printStackTrace();
                 Log.i("Log","ddd");
               }
           //    JSONArray jo = new JSONArray(record);

            //  ArrayList list = bundle.getParcelableArrayList("list");
             //   list2= (List<Object>) list.get(0);

              //  addapter.setData();
              //  mWeatherRv.setAdapter(addapter);


            }



        }
    };*/
    private RecyclerView mWeatherRv;
    static WeatherAdapter addapter = new WeatherAdapter();


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
        getWeather("");
        getAir();
    }

    private boolean getWeather(String location) {
        HeWeather.getWeather(this, location, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onSuccess(List<Weather> list) {
                final List<Object> arrays_obj = new ArrayList<>();
                Alist.MyDataList.NowList nl = new Alist.MyDataList.NowList();
                nl.setNowCondTxt(list.get(0).getNow().getCond_txt());
                nl.setNowTem(list.get(0).getNow().getTmp() + "℃");
                nl.setTolTem(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃ / " + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                arrays_obj.add(nl);
                //Log.i("Log", "ONSU" + nl.getCurTem());
                TextView lo = (TextView)findViewById(R.id.showCity);
                lo.setText(list.get(0).getBasic().getLocation());
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

                Alist.MyDataList.AirList al = new Alist.MyDataList.AirList();
                arrays_obj.add(al);

                Alist.MyDataList.WindList wl = new Alist.MyDataList.WindList();
                wl.setWindDir(list.get(0).getDaily_forecast().get(0).getWind_dir());
                wl.setWindSc(list.get(0).getDaily_forecast().get(0).getWind_sc());
                arrays_obj.add(wl);

                Alist.MyDataList.TipList tl = new Alist.MyDataList.TipList();
                tl.setTComf(list.get(0).getLifestyle().get(0).getTxt());
                tl.setTDrsg(list.get(0).getLifestyle().get(1).getTxt());
                tl.setTFlu(list.get(0).getLifestyle().get(2).getTxt());
                tl.setTSpor(list.get(0).getLifestyle().get(3).getTxt());
                arrays_obj.add(tl);
                addapter.setData(arrays_obj);
                mWeatherRv.setAdapter(addapter);
/*
                Message msg=new Message();
                Bundle b=new Bundle();


                ArrayList aa = new ArrayList(); //这个aa用于在budnle中传递 需要传递的ArrayList<Object>

                aa.add(arrays_obj);

                b.putParcelableArrayList("aa",aa);
                msg.setData(b);
                msg.what=1;
                handler.sendMessage(msg);*/

/*
                Alist.MyDataList.AirList al = new Alist.MyDataList.AirList();
                arrays_obj.add(al);
*/
            }
        });
        return true;
    }

    private void getAir(){
        HeWeather.getAir(this, "武汉", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultAirBeanListener() {
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onSuccess(List<Air> list) {
                Log.i("Log","onAir"+new Gson().toJson(list));

                Bundle b = new Bundle();
                ArrayList<String> record=new ArrayList<>();
                record.add(list.get(0).getAir_now_city().getAqi());
                record.add(list.get(0).getAir_now_city().getPm25());
                record.add(list.get(0).getAir_now_city().getNo2());
                record.add(list.get(0).getAir_now_city().getSo2());
                record.add(list.get(0).getAir_now_city().getO3());
                record.add(list.get(0).getAir_now_city().getCo());
                b.putStringArrayList("air",record);
                Message msg=new Message();
                msg.setData(b);
                msg.what=2;
              //  handler.sendMessage(msg);
             /*   TextView api= (TextView)findViewById(R.id.Aqi);
                api.setText(list.get(0).getAir_now_city().getAqi());

                TextView No2=(TextView)findViewById(R.id.NO2);
                No2.setText(list.get(0).getAir_now_city().getNo2());

                TextView So2= (TextView)findViewById(R.id.SO2);
                So2.setText(list.get(0).getAir_now_city().getSo2());

                TextView Pm=(TextView)findViewById(R.id.Pm2_5);
                Pm.setText(list.get(0).getAir_now_city().getPm25());

                TextView Co= (TextView)findViewById(R.id.CO);
                Co.setText(list.get(0).getAir_now_city().getCo());

                TextView o3 = (TextView)findViewById(R.id.O3);
                o3.setText(list.get(0).getAir_now_city().getO3());*/
            }
        });
    }
}
