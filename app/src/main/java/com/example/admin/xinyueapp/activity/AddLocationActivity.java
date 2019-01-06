package com.example.admin.xinyueapp.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;
public class AddLocationActivity extends StartActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        SearchView locationSearch = (SearchView) findViewById(R.id.location_search);
        locationSearch.setIconifiedByDefault(false);
    }
}
 