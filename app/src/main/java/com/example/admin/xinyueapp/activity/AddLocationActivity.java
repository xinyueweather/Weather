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
    private final ArrayList<String> searchResults = new ArrayList<String>();
    private final ArrayList<String> admins = new ArrayList<String>();
    private final ArrayList<String> cids = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        locations=(ListView)findViewById(R.id.locations);
        locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,searchResults));
        locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,admins));
        //locations.setTextFilterEnabled(true);

        locationSearch=(SearchView)findViewById(R.id.location_search);
        //设置该SearchView默认是否自动缩小为图标
        locationSearch.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        locationSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        locationSearch.setSubmitButtonEnabled(true);


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
        if(TextUtils.isEmpty(newText))
        {
            //清除ListView的过滤
            locations.clearTextFilter();
        }
        else
        {
            //使用用户输入的内容对ListView的列表项进行过滤
            HeWeather.getSearch(this, newText, "world", 10, Lang.CHINESE_SIMPLIFIED, new HeWeather.OnResultSearchBeansListener() {
                @Override
                public void onError(Throwable throwable) {
                    Log.i("Log", "onError: ", throwable);
                }

                @Override
                public void onSuccess(Search search) {
                    Log.i("Log", "onSuccess: " + new Gson().toJson(search));
                    Gson gson = new Gson();
                    String jsondata = gson.toJson(search);          //把dataObject转换成json字符串

                    Log.i("Log", "onSuccess0: " + jsondata);
                    try
                    {
                        Log.i("Log", "onSuccess1: try.");
                        JSONArray jsonArray = new JSONArray(jsondata);
                        Log.i("Log", "onSuccess1: try,try.");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Log.i("Log", "onSuccess1: try,try,try." + jsonObject);
                        JSONObject nowBases = jsonObject.getJSONObject("basic");
                        for (int i=0; i < nowBases.length(); i++){
                            Log.i("Log", "onSuccess1: " + i);
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
                }

            });
            locations.setFilterText(newText);

        }
        return true;
    }
    //单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        //实际应用中应该在该方法内执行实际查询
        //此处仅使用Toast显示用户输入的查询内容
        locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,searchResults));
        locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,admins));
        return true;
    }
}