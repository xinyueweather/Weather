package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderWind extends RecyclerView.ViewHolder {
    TextView mWindDir;
    TextView mWindSc;



    public ViewHolderWind(View view){
        super(view);
        mWindDir = (TextView)view.findViewById(R.id.WindDir);
        mWindSc = (TextView) view.findViewById(R.id.WindSc);

    }
}
