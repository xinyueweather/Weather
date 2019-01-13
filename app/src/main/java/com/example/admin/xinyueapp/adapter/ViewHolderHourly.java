package com.example.admin.xinyueapp.adapter;

import android.support.annotation.MainThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderHourly extends  RecyclerView.ViewHolder{
    TextView mhTimeTv;
    ImageView mhCondIv;
    TextView mhTmpTv;

    TextView mhTimeTv1;
    ImageView mhCondIv1;
    TextView mhTmpTv1;

    TextView mhTimeTv2;
    ImageView mhCondIv2;
    TextView mhTmpTv2;

    TextView mhTimeTv3;
    ImageView mhCondIv3;
    TextView mhTmpTv3;

    TextView mhTimeTv4;
    ImageView mhCondIv4;
    TextView mhTmpTv4;

    TextView mhTimeTv5;
    ImageView mhCondIv5;
    TextView mhTmpTv5;

    TextView mhTimeTv6;
    ImageView mhCondIv6;
    TextView mhTmpTv6;

    TextView mhTimeTv7;
    ImageView mhCondIv7;
    TextView mhTmpTv7;


    public ViewHolderHourly(View view){
        super(view);

        mhTimeTv = (TextView)view.findViewById(R.id.hTime);
        mhCondIv = (ImageView)view.findViewById(R.id.hCond);
        mhTmpTv = (TextView)view.findViewById(R.id.hTmp);

        mhTimeTv1 = (TextView)view.findViewById(R.id.hTime1);
        mhCondIv1 = (ImageView)view.findViewById(R.id.hCond1);
        mhTmpTv1 = (TextView)view.findViewById(R.id.hTmp1);

        mhTimeTv2 = (TextView)view.findViewById(R.id.hTime2);
        mhCondIv2 = (ImageView)view.findViewById(R.id.hCond2);
        mhTmpTv2 = (TextView)view.findViewById(R.id.hTmp2);

        mhTimeTv3 = (TextView)view.findViewById(R.id.hTime3);
        mhCondIv3 = (ImageView)view.findViewById(R.id.hCond3);
        mhTmpTv3 = (TextView)view.findViewById(R.id.hTmp3);

        mhTimeTv4 = (TextView)view.findViewById(R.id.hTime4);
        mhCondIv4 = (ImageView)view.findViewById(R.id.hCond4);
        mhTmpTv4 = (TextView)view.findViewById(R.id.hTmp4);

        mhTimeTv5 = (TextView)view.findViewById(R.id.hTime5);
        mhCondIv5 = (ImageView)view.findViewById(R.id.hCond5);
        mhTmpTv5 = (TextView)view.findViewById(R.id.hTmp5);

        mhTimeTv6 = (TextView)view.findViewById(R.id.hTime6);
        mhCondIv6 = (ImageView)view.findViewById(R.id.hCond6);
        mhTmpTv6 = (TextView)view.findViewById(R.id.hTmp6);

        mhTimeTv7 = (TextView)view.findViewById(R.id.hTime7);
        mhCondIv7 = (ImageView)view.findViewById(R.id.hCond7);
        mhTmpTv7 = (TextView)view.findViewById(R.id.hTmp7);

    }
}
