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
   // private final ArrayList<String> admins = new ArrayList<>();
  //  private final ArrayList<String> cids = new ArrayList<>();
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
        /*Bundle bundle=new Bundle();
        bundle.putStringArrayList("now",nowResults);*/

        HeConfig.init("HE1901031637041115" , "c70ff52ffb0d4dd693d65182c455459b");
        HeConfig.switchToFreeServerNode();
       /// aaa();
        getWeather();


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
/*
        WeatherAdapter addapter = new WeatherAdapter();
        mWeatherRv.setAdapter(addapter);
        // addapter.setData(arrays_obj);
        addapter.setData(new AlistModel().getData());*/

    }
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
    private boolean getWeather() {
        HeWeather.getWeather(this, "武汉", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) { }
            @Override
            public void onSuccess(List<Weather> list) {
                Log.i("Log", "scenic" + new Gson().toJson(list));
                Log.i("Log","SCENIC"+new Gson().toJson(list.get(0).getLifestyle()));
                Log.i("Log","SCENIC"+new Gson().toJson(list.get(0).getNow()));
                Log.i("Log","SCENIC"+new Gson().toJson(list.get(0).getBasic()));
                Log.i("Log","SCENIC"+new Gson().toJson(list.get(0).getStatus()));
                Log.i("Log","SCENIC"+new Gson().toJson(list.get(0).getUpdate()));
                TextView tv_nowTem = (TextView) findViewById(R.id.nowTem);
                TextView tv_nowCond = (TextView) findViewById(R.id.nowCond);
                TextView tv_curMax_MinTem = (TextView) findViewById(R.id.curMax_MinTem);
                /**
                 * 存放缓存天气数据
                 * SharedPreferences.Editor editor = prefs.edit();
                 */
                SharedPreferences prefs = getSharedPreferences("weather", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                tv_nowCond.setText(list.get(0).getNow().getCond_txt());
                editor.putString("nowCond", list.get(0).getNow().getCond_txt());
                tv_nowTem.setText(list.get(0).getNow().getTmp() + "℃");
                editor.putString("nowTem", list.get(0).getNow().getTmp() + "℃");
                tv_curMax_MinTem.setText(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                editor.putString("Max_MinTem", list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                for (int i = 0; i < 3; i++) {
                    /**
                     * tv_dTime1.setText(list.get(0).getDaily_forecast().get(1).getDate());
                     * tv_dMax_MinTem1.setText(list.get(0).getDaily_forecast().get(1).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(1).getTmp_min());
                     * dTime的id,日期
                     */

                    Log.i("Log", "LLLid" + i);
                    int TimeId = getResources().getIdentifier("dTime" + (i+1), "id", "com.example.admin.xinyueapp");
                    TextView dTimeId = findViewById(TimeId);
                    dTimeId.setText(list.get(0).getDaily_forecast().get(i).getDate());
                    editor.putString("dTime" + (i+1), list.get(0).getDaily_forecast().get(i).getDate());
                    //dMax_MinTem的id，最高最低温度
                    int Max_MinTemId = getResources().getIdentifier("dMax_MinTem" + (i+1), "id", "com.example.admin.xinyueapp");
                    TextView dMax_MinTemId = findViewById(Max_MinTemId);
                    dMax_MinTemId.setText(list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    editor.putString("dMax_MinTem" + (i+1), list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    //dCond的Id//dCond对应的image的id
                    int CondId = getResources().getIdentifier("dCond" + (i+1), "id", getPackageName());
                    ImageView dCondId = findViewById(CondId);
                    int imageId = getResources().getIdentifier("w" + list.get(0).getDaily_forecast().get(i).getCond_code_d(), "drawable", "com.example.admin.xinyueapp");
                    dCondId.setImageResource(imageId);
                    editor.putInt("dCond" + (i+1), imageId);
                }
                for (int i = 0; i < 4; i++) {
                    Log.i("Log", "LLLLid" + i);
                    int comfId = getResources().getIdentifier("comtWet" + (i+1), "id", "com.example.admin.xinyueapp");
                    TextView comfText = findViewById(comfId);
                    comfText.setText(list.get(0).getLifestyle().get(i).getBrf() + "\n" + list.get(0).getLifestyle().get(i).getTxt());
                    editor.putString("comtWet" + (i+1), list.get(0).getLifestyle().get(i).getBrf() + "\n" + list.get(0).getLifestyle().get(i).getTxt());
                }
                editor.commit();

                List<Object> arrays_obj=new ArrayList<>();
                Alist.MyDataList.NowList nl= new Alist.MyDataList.NowList();
                nl.setNowCondTxt(list.get(0).getNow().getCond_txt());
                nl.setCurTem(list.get(0).getNow().getTmp() + "℃");
                nl.setTolTem(list.get(0).getDaily_forecast().get(0).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(0).getTmp_min() + "℃");

                arrays_obj.add(nl);
                //Log.i("Log", "ONSU" + nl.getCurTem());
                for(int i=0;i<list.get(0).getDaily_forecast().size();i++){
                    Alist.MyDataList.DaysList dl=new Alist.MyDataList.DaysList();
                    dl.setDate(list.get(0).getDaily_forecast().get(i).getDate());
                    dl.setDayTem(list.get(0).getDaily_forecast().get(i).getTmp_max() + "℃/" + list.get(0).getDaily_forecast().get(i).getTmp_min() + "℃");
                    arrays_obj.add(dl);
                }
               /* Alist.MyDataList.DaysList dl = new Alist.MyDataList.DaysList();
                dl.setDate("2015-158");
                dl.setDayTem("dfjslkdfz");

                arrays_obj.add(dl);*/
                //return sortData(alist);
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

}

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


