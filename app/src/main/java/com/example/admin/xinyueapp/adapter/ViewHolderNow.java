package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.admin.xinyueapp.R;

public class ViewHolderNow extends RecyclerView.ViewHolder {
    TextView mCurTemTv;
    TextView mCurStatusTv;
    TextView mTolTemTv;
    TextView mAirQu;
    TextView mUpdateTv;

    public ViewHolderNow(View view){
        super(view);
        mCurTemTv = (TextView)view.findViewById(R.id.curTem);
        mCurStatusTv = (TextView)view.findViewById(R.id.curStatus);
        mTolTemTv = (TextView)view.findViewById(R.id.tolTem);
        mAirQu = (TextView)view.findViewById(R.id.airQui);
        mUpdateTv = (TextView)view.findViewById(R.id.updateT);
    }
}
