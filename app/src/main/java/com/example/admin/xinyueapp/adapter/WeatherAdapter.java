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
    private int ITEM_NOW = 1;
    private int ITEM_HOURLY = 2;
    private int ITEM_DAYS = 3;
    private int ITEM_COMF = 4;
    private int ITEM_AIR = 5;
    private int ITEM_WIND = 6;
    private int ITEM_TIP = 7;
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
        } else if (viewType == 3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_view, parent, false);
            holder = new ViewHolderDays(view);
        }else if(viewType ==2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_view,parent,false);
            holder = new ViewHolderHourly(view);
        }else if(viewType ==4){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comf_view,parent,false);
            holder = new ViewHolderComf(view);
        }else if(viewType ==5){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.air_view,parent,false);
            holder = new ViewHolderAir(view);
        }else if(viewType ==6){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wind_view,parent,false);
            holder = new ViewHolderWind(view);
        }else if(viewType ==7){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_view,parent,false);
            holder = new ViewHolderTip(view);
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
        }else if (holder instanceof ViewHolderDays) {
            Alist.MyDataList.DaysList data = (Alist.MyDataList.DaysList) objects.get(position);
            ((ViewHolderDays) holder).mDateTv.setText(data.getDate());
            ((ViewHolderDays) holder).mDayStaIv.setImageResource(data.getDCondIma());
            ((ViewHolderDays) holder).mDayTemTv.setText(data.getDayTem());
        }else if(holder instanceof ViewHolderHourly){
            Alist.MyDataList.HourlyList data = (Alist.MyDataList.HourlyList) objects.get(position);
            ((ViewHolderHourly) holder).mhTimeTv.setText(data.gethTime());
            ((ViewHolderHourly) holder).mhCondIv.setImageResource(data.gethCondIma());
            ((ViewHolderHourly) holder).mhTmpTv.setText(data.gethTmp());

            ((ViewHolderHourly) holder).mhTimeTv1.setText(data.gethTime1());
            ((ViewHolderHourly) holder).mhCondIv1.setImageResource(data.gethCondIma1());
            ((ViewHolderHourly) holder).mhTmpTv1.setText(data.gethTmp1());

            ((ViewHolderHourly) holder).mhTimeTv2.setText(data.gethTime2());
            ((ViewHolderHourly) holder).mhCondIv2.setImageResource(data.gethCondIma2());
            ((ViewHolderHourly) holder).mhTmpTv2.setText(data.gethTmp2());

            ((ViewHolderHourly) holder).mhTimeTv3.setText(data.gethTime3());
            ((ViewHolderHourly) holder).mhCondIv3.setImageResource(data.gethCondIma3());
            ((ViewHolderHourly) holder).mhTmpTv3.setText(data.gethTmp3());

            ((ViewHolderHourly) holder).mhTimeTv4.setText(data.gethTime4());
            ((ViewHolderHourly) holder).mhCondIv4.setImageResource(data.gethCondIma4());
            ((ViewHolderHourly) holder).mhTmpTv4.setText(data.gethTmp4());

            ((ViewHolderHourly) holder).mhTimeTv5.setText(data.gethTime5());
            ((ViewHolderHourly) holder).mhCondIv5.setImageResource(data.gethCondIma5());
            ((ViewHolderHourly) holder).mhTmpTv5.setText(data.gethTmp5());

            ((ViewHolderHourly) holder).mhTimeTv6.setText(data.gethTime6());
            ((ViewHolderHourly) holder).mhCondIv6.setImageResource(data.gethCondIma6());
            ((ViewHolderHourly) holder).mhTmpTv6.setText(data.gethTmp6());

            ((ViewHolderHourly) holder).mhTimeTv7.setText(data.gethTime7());
            ((ViewHolderHourly) holder).mhCondIv7.setImageResource(data.gethCondIma7());
            ((ViewHolderHourly) holder).mhTmpTv7.setText(data.gethTmp7());
        }else if(holder instanceof ViewHolderComf){
           Alist.MyDataList.ComfList data = (Alist.MyDataList.ComfList) objects.get(position);
            ((ViewHolderComf) holder).mCFi.setText(data.getCFi());
            ((ViewHolderComf) holder).mCHum.setText(data.getCHum());
            ((ViewHolderComf) holder).mCUv.setText(data.getCUv());
        }else if(holder instanceof ViewHolderAir){
          /*  Alist.MyDataList.AirList data = (Alist.MyDataList.AirList) objects.get(position);
            ((ViewHolderAir) holder).mPM2_5.setText(data.getPM2_5());
            ((ViewHolderAir) holder).mNO2.setText(data.getNO2());
            ((ViewHolderAir) holder).mSO2.setText(data.getSO2());
            ((ViewHolderAir) holder).mCO.setText(data.getCO());
            ((ViewHolderAir) holder).mO3.setText(data.getO3());
            ((ViewHolderAir) holder).mAqi.setText(data.getAqi());*/
        }else if(holder instanceof ViewHolderWind){
            Alist.MyDataList.WindList data = (Alist.MyDataList.WindList) objects.get(position);
            ((ViewHolderWind) holder).mWindSc.setText(data.getWindSc());
            ((ViewHolderWind) holder).mWindDir.setText(data.getWindDir());
        }else if(holder instanceof ViewHolderTip){
            Alist.MyDataList.TipList data = (Alist.MyDataList.TipList) objects.get(position);
            ((ViewHolderTip) holder).mtSpor.setText(data.getTSpor());
            ((ViewHolderTip) holder).mtComf.setText(data.getTComf());
            ((ViewHolderTip) holder).mtDrsg.setText(data.getTDrsg());
            ((ViewHolderTip) holder).mtFlu.setText(data.getTFlu());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof Alist.MyDataList.NowList) {
            return ITEM_NOW;
        }else if (objects.get(position) instanceof Alist.MyDataList.DaysList) {
            return ITEM_DAYS;
        }else if(objects.get(position) instanceof  Alist.MyDataList.HourlyList){
            return ITEM_HOURLY;
        }else if(objects.get(position) instanceof  Alist.MyDataList.ComfList){
            return ITEM_COMF;
        }else if(objects.get(position) instanceof  Alist.MyDataList.AirList){
            return ITEM_AIR;
        }else if(objects.get(position) instanceof  Alist.MyDataList.WindList){
            return ITEM_WIND;
        }else if(objects.get(position) instanceof  Alist.MyDataList.TipList){
            return ITEM_TIP;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return objects == null ? 0 : objects.size();
    }
}