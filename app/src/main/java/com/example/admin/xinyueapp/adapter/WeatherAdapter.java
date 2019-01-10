package com.example.admin.xinyueapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.entity.Alist;
import com.example.admin.xinyueapp.entity.MyNowList;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM_NOW = 1;
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
            Alist.MyDataList.NowList data = (Alist.MyDataList.NowList) objects.get(position);
            ((ViewHolderNow) holder).mCurTemTv.setText(data.getNowTmp());
            ((ViewHolderNow) holder).mCurStatusTv.setText(data.getNowCondTxt());
            ((ViewHolderNow) holder).mTolTemTv.setText(data.getTolTem());
        } else if (holder instanceof ViewHolderDays) {
            Alist.MyDataList.DaysList data = (Alist.MyDataList.DaysList) objects.get(position);
            ((ViewHolderDays) holder).mDateTv.setText(data.getDate());
            ((ViewHolderDays) holder).mDayStaIv.setImageResource(data.getDCondIma());
            ((ViewHolderDays) holder).mDayTemTv.setText(data.getDayTem());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof Alist.MyDataList.NowList) {
            return ITEM_NOW;
        } else if (objects.get(position) instanceof Alist.MyDataList.DaysList) {
            return ITEM_DAYS;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return objects == null ? 0 : objects.size();
    }
}