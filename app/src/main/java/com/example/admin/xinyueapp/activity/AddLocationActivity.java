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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.admin.xinyueapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class AddLocationActivity extends Activity implements SearchView.OnQueryTextListener {


    private SearchView locationSearch;
    private ListView locations;
    //自动完成的列表
    ArrayList<String> searchResults = new ArrayList<>();
    ArrayList<String> admins = new ArrayList<>();
    ArrayList<String> cids = new ArrayList<>();
    ArrayList<HashMap<String, String>> itemsShown = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        locations=(ListView)findViewById(R.id.locations);
        freshList();
        //locations.setTextFilterEnabled(true);
        //点击列表项时激发该方法
        locations.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddLocationActivity.this, "您选择了第" + id + "个项目, 该地点是：" + itemsShown.get(position) + ", 它的 cid 是: "+ cids.get(position), Toast.LENGTH_LONG).show();
            }
        });

        locationSearch=(SearchView)findViewById(R.id.location_search);
        //设置该SearchView默认是否自动缩小为图标
        locationSearch.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        locationSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        locationSearch.setSubmitButtonEnabled(true);
    }

    public boolean clearList(){
        //清空所有ArrayList
        if (searchResults.size()>0){
            for(int i = searchResults.size() ; i > 0 ; i-- ){
                searchResults.remove(0);
            }
        }
        if (admins.size()>0){
            for(int i = admins.size() ; i > 0 ; i-- ){
                admins.remove(0);
            }
        }
        if (cids.size()>0){
            for(int i = cids.size() ; i > 0 ; i-- ){
                cids.remove(0);
            }
        }
        if (itemsShown.size()>0){
            for(int i = itemsShown.size() ; i > 0 ; i-- ){
                itemsShown.remove(0);
            }
        }
        return true;
    }

    public boolean freshList(){
        locations.setAdapter(new SimpleAdapter(
                this,
                itemsShown,//数据来源
                R.layout.item_add_location,//ListItem的XML实现
                new String[] {"locationText", "adminText"},//与ListItem对应的子项
                new int[] {R.id.locationText,R.id.adminText//item_add_location的XML文件里面的两个TextView的id
                }));
        //locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemsShown));
        //locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,admins));
        return true;
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
        if(TextUtils.isEmpty(newText)) {} else {
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
                    try {
                        JSONObject jsonObject = new JSONObject(jsondata); //将字符串格式的jsondata转为jsonobject
                        JSONArray basic = jsonObject.getJSONArray("basic");//获取basic字段，也就是真正有用的数据部分
                        for (int i=0; i < basic.length(); i++){
                            JSONObject nowBases = basic.getJSONObject(i);//获取第 i 个JSON object
                            searchResults.add(nowBases.getString("location"));
                            admins.add(nowBases.getString("admin_area") + " , " + nowBases.getString("parent_city"));
                            cids.add(nowBases.getString("cid"));
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("locationText",searchResults.get(i));
                            item.put("adminText",admins.get(i));
                            itemsShown.add(item);
                            //Log.i("Log","onSuccess: " + searchResults.get(i) + " , " + admins.get(i) + " , " + cids.get(i));
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    freshList();
                }

            });
            //locations.setFilterText(newText);
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
                    searchResults.add(nowBases.getString("location"));
                    admins.add(nowBases.getString("admin_area") + " , " + nowBases.getString("parent_city"));
                    cids.add(nowBases.getString("cid"));
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("locationText",searchResults.get(0));
                    item.put("adminText", admins.get(0));
                    itemsShown.add(item);
                    //rLog.i("Log","onSuccess: " + searchResults.get(0) + " , " + admins.get(0) + " , " + cids.get(0));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                freshList();
            }

        });
    }
}