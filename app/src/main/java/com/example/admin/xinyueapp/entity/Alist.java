package com.example.admin.xinyueapp.entity;


import android.media.Image;

import java.util.List;

/**
 *  code???
 *  data: ["summaryList":[{"curTem":"6℃","curStatus":"阴","tolTem":"6℃/0℃"}],"daysList":["date":"1月6日明天",....] ]
 */
public class Alist {
    private int code;
    private String message;
    private List<MyDataList> data;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<MyDataList> getData() {
        return data;
    }
    public void setData(List<MyDataList> data) {
        this.data = data;
    }

    public static class MyDataList{
        /**
         *  code???
         *  summaryList:[{"curTem":"6℃","curStatus":"阴","tolTem":"6℃/0℃"}]
         *  daysList:["date":"1月6日明天",....] ]
         */
        /**
         * 第一模块 now模块
         */
        private List<NowList> nowList;
        public void setNowList(List<NowList> nowList){ this.nowList=nowList; }
        public List<NowList> getNowList(){ return this.nowList; }

        /**
         * 第二模块 days模块
         */
        private List<DaysList> daysList;
        public void setDaysList(List<DaysList> daysList){ this.daysList=daysList; }
        public List<DaysList> getDaysList(){return this.daysList;}

        /**
         * 第三模块 hourly模块
         */
        private List<HourlyList> hourlyList;
        public void setHourlysList(List<HourlyList> HourlyList){ this.hourlyList=hourlyList; }
        public List<HourlyList> getHourlyList(){return this.hourlyList;}

        /////////第一模块定义
        public static class NowList{
            /**
             *  summaryList:[{"curTem":"6℃","curStatus":"阴","tolTem":"6℃/0℃"
             */
            private String nowTmp;
            private String nowCondTxt;
            private String tolTem;

            public String getNowTmp(){return this.nowTmp;}
            public void setNowTem(String nowTmp){this.nowTmp=nowTmp;}

            public String getNowCondTxt(){return this.nowCondTxt;}
            public void setNowCondTxt(String nowCondTxt){this.nowCondTxt=nowCondTxt;}

            public String getTolTem(){return this.tolTem;}
            public void setTolTem(String tolTem){this.tolTem=tolTem;}
        }
        ///////////第二模块定义
        public static class DaysList{
            /**
             *  date : 1月6日明天
             *  daySta : 阴
             *  dayTem  :  6 / 7
             */
            private String date;
            private int dCondIma;
            private String dayTem;

            public String getDate(){return this.date;}
            public void setDate(String date){this.date=date;}

            public int getDCondIma(){return this.dCondIma;}
            public void setDCondIma(int dCondIma){this.dCondIma=dCondIma;}

            public String getDayTem(){return this.dayTem;}
            public void setDayTem(String dayTem){this.dayTem=dayTem;}
        }

        ///////////第三模块定义
        public static class HourlyList{
            private String hTime;
            private String hTmp;
            private int hCondIma;

            public String gethTime(){ return this.hTime; }
            public void sethTime(String hTime){this.hTime=hTime;}

            public String gethTmp(){return this.hTmp;}
            public void sethTmp(String hTmp){ this.hTmp=hTmp;}

            public int gethCondIma(){ return this.hCondIma;}
            public void sethCondIma(int hCondIma){this.hCondIma=hCondIma;}

        }

        ///////////第四模块定义
        public static class ComfList{
            private String cFi;
            private String cHum;
            private String cUv;

            public String getCFi(){ return this.cFi; }
            public void setCFi(String cFi){this.cFi=cFi;}

            public String getCUv(){ return this.cUv; }
            public void setCUv(String cUv){this.cUv=cUv;}

            public String getCHum(){return this.cHum;}
            public void setCHum(String cHum){ this.cHum=cHum;}
        }

        ///////////第五模块定义
        public static class AirList{
            private String Aqi;
            private String PM2_5;
            private String NO2;
            private String SO2;
            private String O3;
            private String CO;

            public String getAqi(){ return this.Aqi; }
            public void setAqi(String Aqi){ this.Aqi=Aqi;}

            public String getPM2_5(){return this.PM2_5;}
            public void setPM2_5(String PM2_5) { this.PM2_5 = PM2_5; }

            public String getNO2(){ return this.NO2; }
            public void setNO2(String NO2){this.NO2=NO2;}

            public String getSO2(){ return this.SO2; }
            public void setSO2(String SO2){this.SO2=SO2;}

            public String getO3(){ return this.O3; }
            public void setO3(String O3){this.NO2=O3;}

            public String getCO(){ return this.CO; }
            public void setCO(String NO2){this.CO=CO;}
        }

        ///////////第六模块定义
        public static class WindList{
            private String windDir;
            private String windSc;

            public String getWindDir(){ return this.windDir; }
            public void setWindDir(String windDir){ this.windDir=windDir;}

            public String getWindSc(){return this.windSc;}
            public void setWindSc(String windSc) { this.windSc = windSc; }

        }
        ///////////第七模块定义
        public static class TipList{
            private String tComf;
            private String tFlu;
            private String tSpor;
            private String tDrsg;

            public String getTComf(){ return this.tComf; }
            public void setTComf(String tComf){ this.tComf=tComf;}

            public String getTFlu(){return this.tFlu;}
            public void setTFlu(String tFlu) { this.tFlu = tFlu; }


            public String getTSpor(){ return this.tSpor; }
            public void setTSpor(String tSpor){ this.tSpor=tSpor;}

            public String getTDrsg(){return this.tDrsg;}
            public void setTDrsg(String tDrsg) { this.tDrsg = tDrsg; }

        }
    }
}
