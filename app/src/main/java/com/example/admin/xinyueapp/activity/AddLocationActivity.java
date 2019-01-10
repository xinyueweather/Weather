package com.example.admin.xinyueapp.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.adapter.LocationAdapter;
import com.example.admin.xinyueapp.entity.Location;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class AddLocationActivity extends Activity implements SearchView.OnQueryTextListener {


    private ListView locationList;
    //自动完成的列表
    ArrayList<Location> locations =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        locationList=(ListView)findViewById(R.id.location_list);
        freshList();
        //点击列表项时激发该方法
        /*locationList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddLocationActivity.this, "您选择了第" + id + "个项目, 该地点是：" + locations.get(position).getLocation() + ", 它属于: " + locations.get(position).getAdmin() + ", 它的 cid 是: "+ locations.get(position).getCid(), Toast.LENGTH_LONG).show();
            }
        });*/

        SearchView locationSearch = (SearchView) findViewById(R.id.location_search);
        //设置该SearchView默认是否自动缩小为图标
        locationSearch.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        locationSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        locationSearch.setSubmitButtonEnabled(true);
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
                            Location currLocation;
                            if(nowBases.has("admin_area"))
                                {currLocation = new Location(nowBases.getString("location") , nowBases.getString("parent_city") , nowBases.getString("admin_area") , nowBases.getString("cid"));}
                            else
                                {currLocation = new Location(nowBases.getString("location") , nowBases.getString("parent_city") , " " , nowBases.getString("cid"));}
                            locations.add(currLocation);
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