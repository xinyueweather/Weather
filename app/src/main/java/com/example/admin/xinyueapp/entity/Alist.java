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

            public String getCurTem(){return this.nowTmp;}
            public void setCurTem(String nowTmp){this.nowTmp=nowTmp;}

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
            private String hTem;
            private int hCondIma;

            public String gethTime(){ return this.hTime; }
            public void sethTime(String hTime){this.hTime=hTime;}

            public String gethTem(){return this.hTem;}
            public void sethTem(String hTem){ this.hTem=hTem;}

            public int gethCondIma(){ return this.hCondIma;}
            public void sethCondIma(int hCondIma){this.hCondIma=hCondIma;}

        }
    }
}
