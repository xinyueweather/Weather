package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderDays extends RecyclerView.ViewHolder {
    TextView mDateTv;
    ImageView mDayStaIv;
    TextView mDayTemTv;

    public ViewHolderDays(View view){
        super(view);
        mDateTv = (TextView)view.findViewById(R.id.date);
        mDayStaIv = (ImageView)view.findViewById(R.id.daySta);
        mDayTemTv = (TextView)view.findViewById(R.id.dayTem);

    }
}
