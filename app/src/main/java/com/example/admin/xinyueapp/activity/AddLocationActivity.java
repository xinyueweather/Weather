package com.example.admin.xinyueapp.activity;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.xinyueapp.R;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.*;

public class AddLocationActivity extends Activity implements SearchView.OnQueryTextListener {


    private SearchView locationSearch;
    private ListView locations;
    //自动完成的列表
    private final String[] mStrings={"北京","上海","广州","武汉","长沙","成都","重庆","天津","青岛"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        locations=(ListView)findViewById(R.id.locations);
        locations.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings));
        locations.setTextFilterEnabled(true);

        locationSearch=(SearchView)findViewById(R.id.location_search);
        //设置该SearchView默认是否自动缩小为图标
        locationSearch.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        locationSearch.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        locationSearch.setSubmitButtonEnabled(true);

        //设置该SearchView内默认显示的提示文本
        locationSearch.setQueryHint("@string/search_location");


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
            //清楚ListView的过滤
            locations.clearTextFilter();
        }
        else
        {
            //使用用户输入的内容对ListView的列表项进行过滤
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
        Toast.makeText(this, "您选择的是："+query, Toast.LENGTH_SHORT).show();
        return true;
    }

}