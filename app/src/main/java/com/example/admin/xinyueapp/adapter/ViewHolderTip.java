package com.example.admin.xinyueapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.xinyueapp.R;

public class ViewHolderTip extends RecyclerView.ViewHolder {
    TextView mtComf;
    TextView mtFlu;
    TextView mtSpor;
    TextView mtDrsg;

    public ViewHolderTip(View view){
        super(view);
        mtFlu = (TextView)view.findViewById(R.id.tFlu);
        mtComf = (TextView)view.findViewById(R.id.tComf);
        mtDrsg = (TextView)view.findViewById(R.id.tdrsg);
        mtSpor = (TextView)view.findViewById(R.id.tSpor);

    }
}
