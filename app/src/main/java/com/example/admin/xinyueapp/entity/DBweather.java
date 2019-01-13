package com.example.admin.xinyueapp.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBweather extends SQLiteOpenHelper {
    public static final String CREATE_WEATHER = "create table Weather("+
            "cid text primary key," +
            "cond_text text,"+
            "cond_code text,"+
            "tmp integer,"+
            "tmp_max integer,"+
            "tmp_min integer)";

    private Context mContext;
    public DBweather(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name ,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHER);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
