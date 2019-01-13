package com.example.admin.xinyueapp.activity;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.adapter.WeatherAdapter;

import com.example.admin.xinyueapp.entity.Alist;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;

import interfaces.heweather.com.interfacesmodule.bean.air.Air;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class HomePageActivity extends StartActivity {

    private  Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            boolean flag=false;
            boolean flag2=false;

            if(msg.what==1) {
                Log.i("Log","onWHAT 2  1"+flag);
            }
            if(msg.what==2 ){
                Log.i("Log","onWHAT 2  2"+flag2);
                ArrayList<String> als = msg.getData().getStringArrayList("air");


                Log.i("Log","onWHAT 2"+als);
                for(int i=0;i<als.size();i++){
                    Log.i("Log","onWHAT 2: "+i+"  "+als.get(i));
                }
                TextView tv = (TextView)findViewById(R.id.airQui);
                String qlty=als.get(6);
                tv.setText(qlty);

                TextView pt = (TextView)findViewById(R.id.updateT);
                String ppt = als.get(7).substring(10);
                pt.setText("发布时间: "+ppt);

                TextView aqi = (TextView)findViewById(R.id.Aqi);
                String Aqi = als.get(0);
                aqi.setText(Aqi);

                int a = Integer.parseInt(Aqi);
                if(a>=0 && a<=50){
                    ImageView iv = (ImageView)findViewById(R.id.aI);
                    iv.setImageResource(R.drawable.aqi1);
                }else if(a>=51 && a<=100){
                    ImageView iv = (ImageView)findViewById(R.id.aI);
                    iv.setImageResource(R.drawable.w101);
                }
                else if(a>=101 && a<=150){
                    ImageView iv = (ImageView)findViewById(R.id.aI);
                    iv.setImageResource(R.drawable.aqi3);
                }else if(a>=151 && a<=200){
                    ImageView iv = (ImageView)findViewById(R.id.aI);
                    iv.setImageResource(R.drawable.aqi4);
                }else if(a>=201 && a<=300){
                    ImageView iv = (ImageView)findViewById(R.id.aI);
                    iv.setImageResource(R.drawable.aqi5);
                }else if(a > 300){
                    ImageView iv = (ImageView)findViewById(R.id.aI);
                    iv.setImageResource(R.drawable.aqi6);
                }
                TextView pm = (TextView)findViewById(R.id.Pm);
                String Pm = als.get(1);
                pm.setText(Pm);

                TextView no = (TextView)findViewById(R.id.NO);
                String NO = als.get(2);
                no.setText(NO);

                TextView so = (TextView)findViewById(R.id.SO);
                String SO = als.get(3);
                so.setText(SO);

                TextView o = (TextView)findViewById(R.id.O);
                String O = als.get(4);
                o.setText(O);

                TextView co = (TextView)findViewById(R.id.CO);
                String CO = als.get(5);
                co.setText(CO);


            }
        }
    };
    private RecyclerView mWeatherRv;
    static WeatherAdapter addapter = new WeatherAdapter();

    @Override
    protected void onNewIntent(Intent intent) {

        if(intent.getStringExtra("cid")!=null)
        {
            getWeather(intent.getStringExtra("cid"));
        }
        super.onNewIntent(intent);
    }

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

        SharedPreferences sp = getSharedPreferences("CID",MODE_PRIVATE);
        //读取数据
        Set<String> sdata;
        sdata = sp.getStringSet("cid",null);

        if(sdata==null){
            getWeather("北京");
        }else{
            String[] cid = (String[])sdata.toArray(new String[sdata.size()]);
            Log.i("Log","onSdata.cid"+cid.length);
            getWeather(cid[0]);
        }

    }

    /**
     * 获得天气详情
     * @param location
     * @return
     */
    private boolean getWeather(final String location) {
        HeWeather.getWeather(this, location, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onSuccess(List<Weather> list) {
                Log.i("Log","onWe "+ list);
                final List<Object> arrays_obj = new ArrayList<>();
                Alist.MyDataList.NowList nl = new Alist.MyDataList.NowList();
                nl.setNowCondTxt(list.get(0).getNow().getCond_txt());
                nl.setNowTem(list.get(0).getNow().getTmp() + "℃");
                nl.setTolTem(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃ / " + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                nl.setAirQui("");

                arrays_obj.add(nl);

                TextView lo = (TextView)findViewById(R.id.showCity);
                lo.setText(list.get(0).getBasic().getLocation());

                //每小时温度
                ///
                Alist.MyDataList.HourlyList hl = new Alist.MyDataList.HourlyList();
                hl.sethTime("现在");
                int image = getResources().getIdentifier("w" + list.get(0).getNow().getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma(image);
                hl.sethTmp(list.get(0).getNow().getTmp() + "℃");

                hl.sethTime1(list.get(0).getHourly().get(0).getTime().substring(11));
                int image1 = getResources().getIdentifier("w" + list.get(0).getHourly().get(0).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma1(image1);
                hl.sethTmp1(list.get(0).getHourly().get(0).getTmp() + "℃");

                hl.sethTime2(list.get(0).getHourly().get(1).getTime().substring(11));
                int image2 = getResources().getIdentifier("w" + list.get(0).getHourly().get(1).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma2(image2);
                hl.sethTmp2(list.get(0).getHourly().get(1).getTmp() + "℃");

                hl.sethTime3(list.get(0).getHourly().get(2).getTime().substring(11));
                int image3 = getResources().getIdentifier("w" + list.get(0).getHourly().get(2).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma3(image3);
                hl.sethTmp3(list.get(0).getHourly().get(2).getTmp() + "℃");

                hl.sethTime4(list.get(0).getHourly().get(3).getTime().substring(11));
                int image4 = getResources().getIdentifier("w" + list.get(0).getHourly().get(3).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma4(image4);
                hl.sethTmp4(list.get(0).getHourly().get(3).getTmp() + "℃");

                hl.sethTime5(list.get(0).getHourly().get(4).getTime().substring(11));
                int image5 = getResources().getIdentifier("w" + list.get(0).getHourly().get(4).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma5(image5);
                hl.sethTmp5(list.get(0).getHourly().get(4).getTmp() + "℃");

                hl.sethTime6(list.get(0).getHourly().get(5).getTime().substring(11));
                int image6 = getResources().getIdentifier("w" + list.get(0).getHourly().get(5).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma6(image6);
                hl.sethTmp6(list.get(0).getHourly().get(5).getTmp() + "℃");

                hl.sethTime7(list.get(0).getHourly().get(6).getTime().substring(11));
                int image7 = getResources().getIdentifier("w" + list.get(0).getHourly().get(6).getCond_code(), "drawable", "com.example.admin.xinyueapp");
                hl.sethCondIma7(image7);
                hl.sethTmp7(list.get(0).getHourly().get(6).getTmp() + "℃");

                arrays_obj.add(hl);

                for (int i = 0; i < list.get(0).getDaily_forecast().size(); i++) {
                    Alist.MyDataList.DaysList dl = new Alist.MyDataList.DaysList();
                    dl.setDate(list.get(0).getDaily_forecast().get(i).getDate());
                    dl.setDayTem(list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃ / " + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    int imageId = getResources().getIdentifier("w" + list.get(0).getDaily_forecast().get(i).getCond_code_d(), "drawable", "com.example.admin.xinyueapp");
                    dl.setDCondIma(imageId);
                    arrays_obj.add(dl);
                }

                Alist.MyDataList.AirList al = new Alist.MyDataList.AirList();

                arrays_obj.add(al);


                Alist.MyDataList.ComfList cl = new Alist.MyDataList.ComfList();
                cl.setCFi(list.get(0).getNow().getFl() + "℃");
                cl.setCHum(list.get(0).getNow().getHum());
                cl.setCUv(list.get(0).getDaily_forecast().get(0).getUv_index());

                arrays_obj.add(cl);



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
                Message msg = new Message();
                msg.what=1;
                handle.sendMessage(msg);
                getAir(location);
            }
        });
        return true;
    }

    private void getAir(String location){
        HeWeather.getAir(this, location, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultAirBeanListener() {
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onSuccess(List<Air> list) {
                Log.i("Log","onAir"+new Gson().toJson(list));

                Bundle b = new Bundle();
               // String ss = list.get(0).getAir_now_city().getQlty();
               // String aqi = list.get(0).getAir_now_city().getAqi();
                ArrayList<String> record=new ArrayList<>();
                record.add(list.get(0).getAir_now_city().getAqi());
                record.add(list.get(0).getAir_now_city().getPm25());
                record.add(list.get(0).getAir_now_city().getNo2());
                record.add(list.get(0).getAir_now_city().getSo2());
                record.add(list.get(0).getAir_now_city().getO3());
                record.add(list.get(0).getAir_now_city().getCo());
                record.add(list.get(0).getAir_now_city().getQlty());
                record.add(list.get(0).getAir_now_city().getPub_time());
                b.putStringArrayList("air",record);

                Message msg=new Message();
                msg.setData(b);
                msg.what=2;
                handle.sendMessage(msg);
            }
        });
    }

}
