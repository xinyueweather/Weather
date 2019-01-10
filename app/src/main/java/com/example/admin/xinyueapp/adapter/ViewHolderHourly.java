package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderHourly extends  RecyclerView.ViewHolder{
    TextView mhTimeTv;
    ImageView mhCondIv;
    TextView mhTmpTv;

    public ViewHolderHourly(View view){
        super(view);
        mhTimeTv = (TextView)view.findViewById(R.id.hTime);
        mhCondIv = (ImageView)view.findViewById(R.id.hCond);
        mhTmpTv = (TextView)view.findViewById(R.id.hTmp);

    }
}
