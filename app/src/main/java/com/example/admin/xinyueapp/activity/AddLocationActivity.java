package com.example.admin.xinyueapp.activity;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.xinyueapp.R;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.*;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class AddLocationActivity extends Activity implements SearchView.OnQueryTextListener {


    private SearchView locationSearch;
    private ListView locations;
    //自动完成的列表
    private final ArrayList<String> searchResults = new ArrayList<>();
    private final ArrayList<String> admins = new ArrayList<>();
    private final ArrayList<String> cids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        locations=(ListView)findViewById(R.id.locations);
        freshList();
        //locations.setTextFilterEnabled(true);

        locationSearch=(SearchView)findViewById(R.id.location_search);
        //设置该SearchView默认是否自动缩小为图标
        locationSearch.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        locationSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        locationSearch.setSubmitButtonEnabled(true);


    }

    public boolean clearList(){
        if (searchResults.size()>0){
            for(int i = searchResults.size() ; i >= 0 ; i-- ){
                searchResults.remove(0);
            }
        }
        if (admins.size()>0){
            for(int i = admins.size() ; i >= 0 ; i-- ){
                admins.remove(0);
            }
        }
        if (cids.size()>0){
            for(int i = cids.size() ; i >= 0 ; i-- ){
                cids.remove(0);
            }
        }
        return true;
    }

    public boolean freshList(){
        locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,searchResults));
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
        if(TextUtils.isEmpty(newText))
        {
            //清除ListView

        }
        else
        {
            //使用用户输入的内容查询并输出结果
            HeWeather.getSearch(this, newText, "world", 10, Lang.CHINESE_SIMPLIFIED, new HeWeather.OnResultSearchBeansListener() {
                //基于用户输入，通过和风API获取查询结果
                @Override
                public void onError(Throwable throwable) {
                    Log.i("Log", "onError: ", throwable);
                }
                @Override
                public void onSuccess(Search search) {
                    Gson gson = new Gson();
                    String jsondata = gson.toJson(search);          //把dataObject转换成json字符串，存储在jsondata中。
                    try
                    {
                        JSONObject jsonObject = new JSONObject(jsondata);
                        JSONArray basic = jsonObject.getJSONArray("basic");
                        for (int i=0; i < basic.length(); i++){
                            JSONObject nowBases = basic.getJSONObject(i);
                            searchResults.add(nowBases.getString("location"));
                            String admin = nowBases.getString("admin_area") + " , " + nowBases.getString("parent_city");
                            admins.add(admin);
                            cids.add(nowBases.getString("cid"));
                            Log.i("Log","onSuccess2: " + searchResults.get(i) + " , " + admins.get(i) + " , " + cids.get(i));
                        }
                    }
                    catch (Exception e)
                    {
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
}