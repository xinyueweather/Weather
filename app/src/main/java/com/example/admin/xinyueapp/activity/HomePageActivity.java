package com.example.admin.xinyueapp.activity;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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

import com.example.admin.xinyueapp.entity.Alist;
import com.example.admin.xinyueapp.entity.AlistModel;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.TextView;


import com.example.admin.xinyueapp.entity.MyNowList;

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
import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;

import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.bean.weather.ScenicWeather;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class HomePageActivity extends StartActivity {


    private RecyclerView mWeatherRv;
   // private Handler  handler;
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

        mWeatherRv =(RecyclerView)findViewById(R.id.recycler_view2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mWeatherRv.setLayoutManager(manager);

        HeConfig.init(this.getString(R.string.id), this.getString(R.string.key));
        HeConfig.switchToFreeServerNode();
        /// aaa();
        getWeather();
    }
    private boolean getWeather() {
        HeWeather.getWeather(this, "武汉", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) { }
            @Override
            public void onSuccess(List<Weather> list) {
                List<Object> arrays_obj=new ArrayList<>();
                Alist.MyDataList.NowList nl= new Alist.MyDataList.NowList();
                nl.setNowCondTxt(list.get(0).getNow().getCond_txt());
                nl.setNowTem(list.get(0).getNow().getTmp() + "℃");
                nl.setTolTem(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                arrays_obj.add(nl);
                //Log.i("Log", "ONSU" + nl.getCurTem());
                for(int i=0;i<list.get(0).getDaily_forecast().size();i++){
                    Alist.MyDataList.DaysList dl=new Alist.MyDataList.DaysList();
                    dl.setDate(list.get(0).getDaily_forecast().get(i).getDate());
                    dl.setDayTem(list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    int imageId = getResources().getIdentifier("w" + list.get(0).getDaily_forecast().get(i).getCond_code_d(), "drawable", "com.example.admin.xinyueapp");
                    dl.setDCondIma(imageId);
                    arrays_obj.add(dl);
                }
                WeatherAdapter addapter = new WeatherAdapter();
                mWeatherRv.setAdapter(addapter);
                addapter.setData(arrays_obj);
                Log.i("Log", "ONSU" + arrays_obj);
                //addapter.setData(new AlistModel().getData());*/
            }
        });
        return true;
    }

    private  boolean  aaa(){
        //   final ArrayList<String> nowResults = new ArrayList<>();
        //HeWeather.getWeatherForecast(Context context, Lang lang, Unit unit, final HeWeather.OnResultWeatherForecastBeanListener listener);
        HeWeather.getWeatherNow(this, "CN101010100", Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Log", "onError: ", e);
                    }
                    @Override
                    //List<NOW>,NOW为和风SDK自带的bean，是“now”，也就是{cloud:0........}
                    public void onSuccess(List<Now> dataObject)
                    {
                        Gson gson = new Gson();
                        String jsondata = gson.toJson(dataObject);          //把dataObject转换成json字符串
                        Log.i("Log", "onSuccess444: " + jsondata);
                        try {
                            JSONArray jsonArray = new JSONArray(jsondata);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject now = jsonArray.getJSONObject(i);
                                JSONObject nowBases = now.getJSONObject("now");
                                Log.i("Log", "onSuccess1: " + i);
                                String tmp = nowBases.getString("tmp");

                                nowResults.add(nowBases.getString("tmp"));
                                nowResults.add(nowBases.getString("cond_txt"));
                                String cond_txt = nowBases.getString("cond_txt");
                                String wind_dir = nowBases.getString("wind_dir");
                                int wind_sc = nowBases.getInt("wind_sc");
                                // TextView textView = (TextView)findViewById(R.id.curTem);
                                //textView.setText(nowResults.get(1));
                                Log.i("Log", "onSuccess1: " + nowResults.get(0) + nowResults.get(1));
/*
                                Bundle b = new Bundle();
                                b.putStringArrayList("now", nowResults);
                                b.putStringArrayList("now2", nowResults);
                                Message msg = new Message();
                                msg.setData(b);
                                msg.what = 150;
                                handler.sendMessage(msg);*/

                                /**
                                 * get NowData
                                 */
/*
                                Alist.MyDataList.NowList nl= new Alist.MyDataList.NowList();
                                nl.setNowCondTxt(cond_txt);
                                nl.setCurTem(tmp);
                                nl.setTolTem("15/28 jiashe");
                                Log.i("Log", "ONSU" + nl.getCurTem());
                                Alist.MyDataList.DaysList dl = new Alist.MyDataList.DaysList();
                                dl.setDate("2015-158");
                                dl.setDayTem("dfjslkdfz");
                                List<Object> arrays_obj=new ArrayList<>();
                                arrays_obj.add(nl);
                                arrays_obj.add(dl);
                                //return sortData(alist);
                                WeatherAdapter addapter = new WeatherAdapter();
                                mWeatherRv.setAdapter(addapter);
                                addapter.setData(arrays_obj);
                                Log.i("Log", "ONSU" + arrays_obj);*/
                                //addapter.setData(new AlistModel().getData());
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        return true;
    }

}
/*

        mWeatherRv =(RecyclerView)findViewById(R.id.recycler_view2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mWeatherRv.setLayoutManager(manager);
/*
        WeatherAdapter addapter = new WeatherAdapter();
        mWeatherRv.setAdapter(addapter);
        // addapter.setData(arrays_obj);
        addapter.setData(new AlistModel().getData());*/


/*
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==150){

                nowResults.add(msg.getData().getStringArrayList("now").get(0));
                nowResults.add(msg.getData().getStringArrayList("now2").get(1));
                Log.i("Log", "onSuccess333: " + nowResults);

            }
        }
    };*/

        /**
         * getNowData
         */
   /*     List<Alist.MyDataList.NowList> summ = new ArrayList<>();
        Alist.MyDataList.NowList nowLists=new Alist.MyDataList.NowList();
        nowLists.setCurTem(nowResults.get(0)+"xxx");
        nowLists.setNowCondTxt(nowResults.get(1));
        summ.add(nowLists);
*/
        /**
         *  getAllData
         */
     /*   List<Alist.MyDataList> datalist = new ArrayList<>();
        Alist.MyDataList myData = new Alist.MyDataList();
        myData.setNowList(summ);
        datalist.add(myData);
        */
        /**
         * getData()
         */
   /*     Alist alist = new Alist();
        alist.setData(datalist);

       // return sortData(alist);

        /**
         * get SortData
         */

        //List<Alist.MyDataList> arrays = alist.getData();
   /*     List<Object> arrays_obj = new ArrayList<>();

        for (Alist.MyDataList array : datalist) {
            List<Alist.MyDataList.NowList> summ1 = array.getNowList();
            if (summ1 != null && summ1.size() > 0) {
                for (Alist.MyDataList.NowList sum : summ1) {
                    arrays_obj.add(sum);
                }
            }
        }
*/



        /*

            List<Alist.MyDataList.NowList> summ = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Alist.MyDataList.NowList mySumm = new Alist.MyDataList.NowList();
                int a = i + 1;
                mySumm.setNowCondTxt("阴" + a);
                mySumm.setCurTem("1" + a + "℃");
                mySumm.setTolTem("10/15℃" + a);
                summ.add(mySumm);
            }



            List<Alist.MyDataList.DaysList> day = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Alist.MyDataList.DaysList myDay = new Alist.MyDataList.DaysList();
                int a = i + 1;
                myDay.setDate("2018.12.11 " + a);
                //  myDay.setDaySta(R.drawable.orange);
                myDay.setDayTem("10/15℃" + a);
                day.add(myDay);
            }


            List<Alist.MyDataList> data = new ArrayList<>();
            Alist.MyDataList myData = new Alist.MyDataList();
            myData.setNowList(getNowData(1));
            data.add(myData);
            for (int i = 0; i <2; i++) {
                myData.setDaysList(getDaysData(i + 1));
                data.add(myData);
            }*/


/*
        HeConfig.init("HE1901050852481925", "f02371a47b794336ad07043678adf705");
        HeConfig.switchToFreeServerNode();

        //HeWeather.getWeatherForecast(Context context, Lang lang, Unit unit, final HeWeather.OnResultWeatherForecastBeanListener listener);
        HeWeather.getWeatherNow(this, "CN101010100", Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Log", "onError: ", e);
                    }
                    @Override
                    //List<NOW>,NOW为和风SDK自带的bean，是“now”，也就是{cloud:0........}
                    public void onSuccess(List<Now> dataObject) {
                        Log.i("Log", "onSuccess: " + new Gson().toJson(dataObject));
                        Log.i("Log", "onSuccess: " + new Gson().toJson(dataObject));
                        Gson gson = new Gson();
                        String jsondata = gson.toJson(dataObject);          //把dataObject转换成json字符串
                        Log.i("Log", "onSuccess: " + jsondata);
                        try
                        {
                            JSONArray jsonArray = new JSONArray(jsondata);
                            for (int i=0; i < jsonArray.length(); i++)    {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject nowBases = jsonObject.getJSONObject("now");
                                        Log.i("Log", "onSuccess1: " + i);
                                int tmp = nowBases.getInt("tmp");
                                String cond_txt = nowBases.getString("cond_txt");
                                String wind_dir = nowBases.getString("wind_dir");
                                int wind_sc = nowBases.getInt("wind_sc");
                                TextView textView = (TextView)findViewById(R.id.curTem);
                                textView.setText(tmp+"℃");
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });*/

