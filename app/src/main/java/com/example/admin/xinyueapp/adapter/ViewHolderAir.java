package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderAir extends RecyclerView.ViewHolder {

    TextView mPM;
    TextView mNO;
    TextView mSO;
    TextView mO;
    TextView mCO;
    TextView mAqi;

    public ViewHolderAir(View view){
        super(view);
        mAqi = (TextView)view.findViewById(R.id.Aqi);
        mPM = (TextView) view.findViewById(R.id.Pm);
        mNO = (TextView)view.findViewById(R.id.NO);
        mSO = (TextView) view.findViewById(R.id.SO);
        mCO = (TextView)view.findViewById(R.id.CO);
        mO = (TextView)view.findViewById(R.id.O);

    }
}
