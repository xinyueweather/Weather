package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderAir extends RecyclerView.ViewHolder {
    //   TextView mCT1;
    TextView mPM2_5;
    TextView mNO2;
    TextView mSO2;
    TextView mO3;
    TextView mCO;
    TextView mAqi;



    public ViewHolderAir(View view){
        super(view);
        mAqi = (TextView)view.findViewById(R.id.Aqi);
        mPM2_5 = (TextView) view.findViewById(R.id.Pm2_5);
        mNO2 = (TextView)view.findViewById(R.id.NO2);
        mSO2 = (TextView) view.findViewById(R.id.SO2);
        mCO = (TextView)view.findViewById(R.id.CO);
        mO3 = (TextView)view.findViewById(R.id.O3);

    }
}
