package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderComf extends RecyclerView.ViewHolder {
 //   TextView mCT1;
    TextView mCFi;
    TextView mCHum;
    TextView mCUv;



    public ViewHolderComf(View view){
        super(view);

        mCFi = (TextView) view.findViewById(R.id.Fi);
        mCHum = (TextView)view.findViewById(R.id.Hum);
        mCUv = (TextView)view.findViewById(R.id.Hv);


    }
}



