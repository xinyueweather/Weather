package com.example.admin.xinyueapp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import com.example.admin.xinyueapp.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button addCity=(Button) findViewById(R.id.addCity);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomePageActivity.this,AddLocationActivity.class);
                startActivity(intent);
            }
        });
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=0;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=0;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
<<<<<<< HEAD

=======
>>>>>>> d29eeeb7ef1bcf270da78953b424c6d151b8b807
        HeConfig.init("HE1901050852481925", "f02371a47b794336ad07043678adf705");
        HeConfig.switchToFreeServerNode();
        HeWeather.getWeatherNow(this, "CN101010100", Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Log", "onError: ", e);
                    }

                    @Override
                    public void onSuccess(List<Now> dataObject) {
                       // Log.i("Log", "onSuccess: " + new Gson().toJson(dataObject));
                        Gson gson = new Gson();
                        String jsondata = gson.toJson(dataObject);
                        //JSONObject jsonObject = new JSONObject(jsondata);
                        Log.i("Log", "onSuccess: " + jsondata);
                        try
                        {
                            JSONArray jsonArray = new JSONArray(jsondata);
                            for (int i=0; i < jsonArray.length(); i++)    {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i("Log", "onSuccess1: " + i);
                                int tmp = jsonObject.getInt("tmp");
                                String cond_txt = jsonObject.getString("cond_txt");
                                String wind_dir = jsonObject.getString("wind_dir");
                                int wind_sc = jsonObject.getInt("wind_sc");
                                TextView textView = (TextView)findViewById(R.id.temp);
                                textView.setText(cond_txt);

                                System.out.println("温度" + tmp + ";天气" + cond_txt + ";风向" + wind_dir + ";风力" + wind_sc);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

    }


}
