package com.example.admin.xinyueapp.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.adapter.WeatherAdapter;

import com.example.admin.xinyueapp.entity.AlistModel;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.xinyueapp.entity.DBweather;
import com.google.gson.Gson;

import com.example.admin.xinyueapp.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.bean.weather.ScenicWeather;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class HomePageActivity extends StartActivity {

    public RecyclerView mWeatherRv;
    private DBweather dbWeather;

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

        ///
        //
        ///
        dbWeather = new DBweather(this,"Weather.db",null,1);
        dbWeather.getWritableDatabase();

        SQLiteDatabase db = dbWeather.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cid","洪山");
        values.put("cond_text","雨夹雪");
        values.put("cond_code","404");
        values.put("tmp",3);
        values.put("tmp_max",6);
        values.put("tmp_min",2);
        //insert（）方法中第一个参数是表名，第二个参数是表示给表中未指定数据的自动赋值为NULL。第三个参数是一个ContentValues对象
        db.insert("Weather",null,values);
        values.clear();
        /*SQLiteDatabase db1 = dbWeather.getWritableDatabase();
        Cursor cursor = db1.query("Weather",null,null,null,null,null,null);
        Log.i("Test","database"+ cursor.getString(cursor.getColumnIndex("cid")));
        if (cursor.moveToFirst()){
            do {
                //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                String cid = cursor.getString(cursor.getColumnIndex("cid"));
                Log.i("Test","database1"+ cid);
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d("MainActivity","book name is "+name);
                Log.d("MainActivity","book author is "+author);
                Log.d("MainActivity","book pages is "+pages);
                Log.d("MainActivity","book price is "+price);
            }while(cursor.moveToNext());
        }
        cursor.close();*/




/*
        mWeatherRv =(RecyclerView)findViewById(R.id.recycler_view2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mWeatherRv.setLayoutManager(manager);

        WeatherAdapter addapter = new WeatherAdapter();
        mWeatherRv.setAdapter(addapter);
        addapter.setData(new AlistModel().getData());

*/
        HeConfig.init("HE1901050852481925", "f02371a47b794336ad07043678adf705");
        HeConfig.switchToFreeServerNode();

        SharedPreferences prefs = getSharedPreferences("weather", Context.MODE_PRIVATE);
        String weatherString = prefs.getString("weather",null);
        Log.i("Log","wstring"+prefs.getAll().toString());
        final String weathId = "洪山区";

        if(weatherString == null){
            requestWeather(weathId);
        }
        else{
            showWeather(prefs);
        }
        //逐小时预报
        //无权限访问，免费用户的悲哀
    }
    private void requestWeather(String location){
        HeWeather.getWeather(this, location, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(List<Weather> list) {
                Log.i("Log", "scenic" + new Gson().toJson(list));
                TextView tv_nowTem = (TextView) findViewById(R.id.nowTem);
                TextView tv_nowCond = (TextView) findViewById(R.id.nowCond);
                TextView tv_curMax_MinTem = (TextView) findViewById(R.id.curMax_MinTem);
                //存放缓存天气数据
                //SharedPreferences.Editor editor = prefs.edit();
                SharedPreferences prefs = getSharedPreferences("weather", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                //TextView tv_comtWet = (TextView) findViewById(R.id.comtWet);
                tv_nowCond.setText(list.get(0).getNow().getCond_txt());
                editor.putString("nowCond",list.get(0).getNow().getCond_txt());
                tv_nowTem.setText(list.get(0).getNow().getTmp() + "℃");
                editor.putString("nowTem",list.get(0).getNow().getTmp() + "℃");
                tv_curMax_MinTem.setText(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                editor.putString("Max_MinTem",list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                for (int i = 0; i < list.get(0).getDaily_forecast().size(); i++) {
                    //tv_dTime1.setText(list.get(0).getDaily_forecast().get(1).getDate());
                    //tv_dMax_MinTem1.setText(list.get(0).getDaily_forecast().get(1).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(1).getTmp_min());
                    //dTime的id,日期
                    Log.i("Log","LLLid"+i);
                    int TimeId = getResources().getIdentifier("dTime" + i, "id", "com.example.admin.xinyueapp");
                    TextView dTimeId = findViewById(TimeId);
                    dTimeId.setText(list.get(0).getDaily_forecast().get(i).getDate());
                    editor.putString("dTime"+i,list.get(0).getDaily_forecast().get(i).getDate());
                    //dMax_MinTem的id，最高最低温度
                    int Max_MinTemId = getResources().getIdentifier("dMax_MinTem" + i, "id", "com.example.admin.xinyueapp");
                    TextView dMax_MinTemId = findViewById(Max_MinTemId);
                    dMax_MinTemId.setText(list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    editor.putString("dMax_MinTem"+i,list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    //dCond的Id//dCond对应的image的id
                    int CondId = getResources().getIdentifier("dCond" + i,"id", getPackageName());
                    ImageView dCondId = findViewById(CondId);
                    int imageId = getResources().getIdentifier("w" + list.get(0).getDaily_forecast().get(i).getCond_code_d(), "drawable", "com.example.admin.xinyueapp");
                    dCondId.setImageResource(imageId);
                    editor.putInt("dCond"+i,imageId);
                }
                for(int i=0; i < 4; i ++){
                    Log.i("Log","LLLLid"+i);
                    int comfId = getResources().getIdentifier("comf"+i,"id", "com.example.admin.xinyueapp");
                    TextView comfText = findViewById(comfId);
                    comfText.setText(list.get(0).getLifestyle().get(i).getBrf() + "\n" + list.get(0).getLifestyle().get(i).getTxt());
                    editor.putString("comf"+i,list.get(0).getLifestyle().get(i).getBrf() + "\n" + list.get(0).getLifestyle().get(i).getTxt());
                }
                editor.commit();
            }
        });

    }
    private void showWeather(SharedPreferences prefs){
        TextView tv_nowTem =  findViewById(R.id.nowTem);
        TextView tv_nowCond =  findViewById(R.id.nowCond);
        TextView tv_curMax_MinTem =  findViewById(R.id.curMax_MinTem);
        //存放缓存天气数据
        SharedPreferences.Editor editor = prefs.edit();
        //TextView tv_comtWet = (TextView) findViewById(R.id.comtWet);
        tv_nowCond.setText(prefs.getString("nowCond","default"));
        tv_nowTem.setText(prefs.getString("nowTem","default"));
        tv_curMax_MinTem.setText(prefs.getString("Max_MinTem","default"));
        for (int i = 0; i < 3; i++) {
            //tv_dTime1.setText(list.get(0).getDaily_forecast().get(1).getDate());
            //tv_dMax_MinTem1.setText(list.get(0).getDaily_forecast().get(1).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(1).getTmp_min());
            //dTime的id,日期
            int TimeId = getResources().getIdentifier("dTime" + i, "id", "com.example.admin.xinyueapp");
            TextView dTimeId = findViewById(TimeId);
            dTimeId.setText(prefs.getString("dTime"+i,"default"));
            //dMax_MinTem的id，最高最低温度
            int Max_MinTemId = getResources().getIdentifier("dMax_MinTem" + i, "id", "com.example.admin.xinyueapp");
            TextView dMax_MinTemId = findViewById(Max_MinTemId);
            dMax_MinTemId.setText(prefs.getString("dMax_MinTem"+i,"default"));
            //editor.putString("dMax_MinTem"+i,list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
            //dCond的Id//dCond对应的image的id
            int CondId = getResources().getIdentifier("dCond" + i,"id", "com.example.admin.xinyueapp");
            ImageView dCondId = findViewById(CondId);
            //int imageId = getResources().getIdentifier("w" + list.get(0).getDaily_forecast().get(i).getCond_code_d(), "drawable", "com.example.admin.xinyueapp");
            int imageId = getResources().getIdentifier("w"+prefs.getInt("imageId", Integer.parseInt("")),"drawable","com.example.admin.xinyueapp");
            dCondId.setImageResource(imageId);
            editor.putInt("dCond"+i,imageId);
        }
        for(int i=0; i < 4; i ++){
            Log.i("Log","LLLLid"+i);
            int comfId = getResources().getIdentifier("comf"+i,"id", "com.example.admin.xinyueapp");
            TextView comfText = findViewById(comfId);
            comfText.setText(prefs.getString("comf"+i,"default"));
            //editor.putString("comf"+i,list.get(0).getLifestyle().get(i).getBrf() + "\n" + list.get(0).getLifestyle().get(i).getTxt());
        }
    }
}


