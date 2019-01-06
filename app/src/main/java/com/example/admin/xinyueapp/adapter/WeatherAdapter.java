package com.example.admin.xinyueapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.entity.Alist;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM_SUMMARY = 1;
    private int ITEM_DAYS = 2;
    private List<Object> objects;

    public void setData(List<Object> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_view, parent, false);
            holder = new ViewHolderNow(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_view, parent, false);
            holder = new ViewHolderDays(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderNow) {
            Alist.MyDataList.MySummaryList data = (Alist.MyDataList.MySummaryList) objects.get(position);
            ((ViewHolderNow) holder).mCurTemTv.setText("6℃");
            ((ViewHolderNow) holder).mCurStatusTv.setText("阴");
            ((ViewHolderNow) holder).mTolTemTv.setText("6℃/0℃");
        } else if (holder instanceof ViewHolderDays) {
            Alist.MyDataList.MyDaysList data = (Alist.MyDataList.MyDaysList) objects.get(position);
            ((ViewHolderDays) holder).mDateTv.setText("1月3日");
            //((ViewHolderDays) holder).mDayStaIv.setImageResource(R.drawable.apple);
            ((ViewHolderDays) holder).mDayTemTv.setText("6℃/0℃");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof Alist.MyDataList.MySummaryList) {
            return ITEM_SUMMARY;
        } else if (objects.get(position) instanceof Alist.MyDataList.MyDaysList) {
            return ITEM_DAYS;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return objects == null ? 0 : objects.size();
    }
}