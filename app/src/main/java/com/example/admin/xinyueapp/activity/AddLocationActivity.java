package com.example.admin.xinyueapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.adapter.LocationAdapter;
import com.example.admin.xinyueapp.entity.Location;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class AddLocationActivity extends Activity implements SearchView.OnQueryTextListener {



    private  int index =0;

    private ListView locationList;
    //自动完成的列表
    ArrayList<Location> locations =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        locationList= findViewById(R.id.location_list);
        freshList();


        //点击列表项时激发该方法
        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 *轻量级缓存城市
                 */
                ////获取SharedPreferences对象
                //Context ctx= AddLocationActivity.this;
                SharedPreferences sp = getSharedPreferences("CID",MODE_PRIVATE);
                //存入数据
                SharedPreferences.Editor editor = sp.edit();

                Set<String> sdata;
                Set<String> sdd = new HashSet<>();

               /* //存入多个城市
                sdata = sp.getStringSet("cid",null);
                if(sdata==null){
                    //Set<String> nn = new HashSet<>();;
                    sdd.add(locations.get(position).getCid());
                    editor.putStringSet("cid",sdd);

                }else{
                    sdata.add(locations.get(position).getCid());
                    editor.clear();
                    editor.putStringSet("cid",sdata);
                }*/
                sdd.add(locations.get(position).getCid());
                sdd.add(locations.get(position).getCity());
                editor.clear();
                editor.putStringSet("cid",sdd);
                editor.apply();

                //页面跳转
                Intent intent = new Intent(AddLocationActivity.this, HomePageActivity.class);
                intent.putExtra("cid",locations.get(position).getLocation());
                intent.putExtra("admin",locations.get(position).getCity());
                startActivity(intent);


            }
        });

        SearchView locationSearch = findViewById(R.id.location_search);
        //设置该SearchView默认是否自动缩小为图标
        locationSearch.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        locationSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        locationSearch.setSubmitButtonEnabled(true);
        View viewById = locationSearch.findViewById(android.support.v7.appcompat.R.id.search_plate);
        if (viewById != null) {
            viewById.setBackgroundColor(Color.TRANSPARENT);
        }
        int id = locationSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = locationSearch.findViewById(id);
        textView.setTextColor(this.getColor(R.color.white));
        textView.setHintTextColor(Color.parseColor("#FFFFFF"));
        //setUnderLinetransparent(locationSearch);
        /*把下划线设为透明，不知道为什么不能用。
        View mSearchPlate = findViewById(R.id.search_plate);
        View mSearchArea = findViewById(R.id.submit_area);
        locationSearch.findViewById(android.support.v7.appcompat.R.id.search_plate).setBackground(null);
        locationSearch.findViewById(android.support.v7.appcompat.R.id.submit_area).setBackground(null);*/




    }

    public void clearList(){
        //清空所有ArrayList
        if (locations.size()>0){
            for(int i = locations.size() ; i > 0 ; i-- ){
                locations.remove(0);
            }
        }
    }

    public void freshList(){
        locationList.setAdapter(new LocationAdapter(this , locations));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_add_location, menu);
        return true;
    }
    //用户输入字符时激发该方法
    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO Auto-generated method stub
        clearList();
        freshList();
        if(!TextUtils.isEmpty(newText)) {
            //使用用户输入的内容查询并输出结果
            HeWeather.getSearch(this, newText, "world", 20, Lang.CHINESE_SIMPLIFIED, new HeWeather.OnResultSearchBeansListener() {
                //基于用户输入，通过和风API获取查询结果
                @Override
                public void onError(Throwable throwable) {
                    Log.i("Log", "onError: ", throwable);
                }
                @Override
                public void onSuccess(Search search) {
                    Gson gson = new Gson();
                    String jsondata = gson.toJson(search);          //把dataObject转换成json字符串，存储在jsondata中。
                    Log.i("Log", "onSuccess: " + jsondata);
                    try {
                        JSONObject jsonObject = new JSONObject(jsondata); //将字符串格式的jsondata转为jsonobject
                        JSONArray basic = jsonObject.getJSONArray("basic");//获取basic字段，也就是真正有用的数据部分
                        for (int i=0; i < basic.length(); i++){
                            JSONObject nowBases = basic.getJSONObject(i);//获取第 i 个JSON object
                            Log.i("Log", "onSuccess: " + nowBases);
                            locations.add(new Location(nowBases.getString("location") , nowBases.getString("parent_city") , nowBases.getString("admin_area") , nowBases.getString("cid")));
                            Log.i("Log", "onSuccess: " + locations.get(i).getAdmin() + locations.get(i).getLocation() + locations.get(i).getCid());
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    freshList();
                }

            });
        }
        return true;
    }
    //单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(),this.getString(R.string.please_click_results),Toast.LENGTH_SHORT).show();
        return true;
    }

    //点击搜索栏右侧的位置图标时激发该方法
    public void searchWithGPS(View view){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                0);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                0);
        clearList();
        freshList();
        HeWeather.getSearch(this, 10, new HeWeather.OnResultSearchBeansListener() {
            //基于用户输入，通过和风API获取查询结果
            @Override
            public void onError(Throwable throwable) {
                Log.i("Log", "onError: ", throwable);
            }
            @Override
            public void onSuccess(Search search) {
                Gson gson = new Gson();
                String jsondata = gson.toJson(search);          //把dataObject转换成json字符串，存储在jsondata中。
                try {
                    JSONObject jsonObject = new JSONObject(jsondata); //将字符串格式的jsondata转为jsonobject
                    JSONArray basic = jsonObject.getJSONArray("basic");//获取basic字段，也就是真正有用的数据部分
                    JSONObject nowBases = basic.getJSONObject(0);//获取第 i 个JSON object
                    locations.add(new Location(nowBases.getString("location") , nowBases.getString("parent_city") , nowBases.getString("admin_area") , nowBases.getString("cid")));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                freshList();
            }

        });
    }
}