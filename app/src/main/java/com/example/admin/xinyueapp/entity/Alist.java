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

            private String hTime1;
            private String hTmp1;
            private int hCondIma1;

            private String hTime2;
            private String hTmp2;
            private int hCondIma2;

            private String hTime3;
            private String hTmp3;
            private int hCondIma3;

            private String hTime4;
            private String hTmp4;
            private int hCondIma4;

            private String hTime5;
            private String hTmp5;
            private int hCondIma5;

            private String hTime6;
            private String hTmp6;
            private int hCondIma6;

            private String hTime7;
            private String hTmp7;
            private int hCondIma7;





            public String gethTime(){ return this.hTime; }
            public void sethTime(String hTime){this.hTime=hTime;}
            public String gethTime1(){ return this.hTime1; }
            public void sethTime1(String hTime1){this.hTime1=hTime1;}
            public String gethTime2(){ return this.hTime2; }
            public void sethTime2(String hTime2){this.hTime2=hTime2;}
            public String gethTime3(){ return this.hTime3; }
            public void sethTime3(String hTime3){this.hTime3=hTime3;}
            public String gethTime4(){ return this.hTime4; }
            public void sethTime4(String hTime4){this.hTime4=hTime4;}
            public String gethTime5(){ return this.hTime5; }
            public void sethTime5(String hTime5){this.hTime5=hTime5;}
            public String gethTime6(){ return this.hTime6; }
            public void sethTime6(String hTime6){this.hTime6=hTime6;}
            public String gethTime7(){ return this.hTime7; }
            public void sethTime7(String hTime7){this.hTime7=hTime7;}

            public String gethTmp(){return this.hTmp;}
            public void sethTmp(String hTmp){ this.hTmp=hTmp;}
            public String gethTmp1(){return this.hTmp1;}
            public void sethTmp1(String hTmp1){ this.hTmp1=hTmp1;}
            public String gethTmp2(){return this.hTmp2;}
            public void sethTmp2(String hTmp2){ this.hTmp2=hTmp2;}
            public String gethTmp3(){return this.hTmp3;}
            public void sethTmp3(String hTmp3){ this.hTmp3=hTmp3;}
            public String gethTmp4(){return this.hTmp4;}
            public void sethTmp4(String hTmp4){ this.hTmp4=hTmp4;}
            public String gethTmp5(){return this.hTmp5;}
            public void sethTmp5(String hTmp5){ this.hTmp5=hTmp5;}
            public String gethTmp6(){return this.hTmp6;}
            public void sethTmp6(String hTmp6){ this.hTmp6=hTmp6;}
            public String gethTmp7(){return this.hTmp7;}
            public void sethTmp7(String hTmp7){ this.hTmp7=hTmp7;}

            public int gethCondIma(){ return this.hCondIma;}
            public void sethCondIma(int hCondIma){this.hCondIma=hCondIma;}
            public int gethCondIma1(){ return this.hCondIma1;}
            public void sethCondIma1(int hCondIma1){this.hCondIma1=hCondIma1;}
            public int gethCondIma2(){ return this.hCondIma2;}
            public void sethCondIma2(int hCondIma2){this.hCondIma2=hCondIma2;}
            public int gethCondIma3(){ return this.hCondIma3;}
            public void sethCondIma3(int hCondIma3){this.hCondIma3=hCondIma3;}
            public int gethCondIma4(){ return this.hCondIma4;}
            public void sethCondIma4(int hCondIma4){this.hCondIma4=hCondIma4;}
            public int gethCondIma5(){ return this.hCondIma5;}
            public void sethCondIma5(int hCondIma5){this.hCondIma5=hCondIma5;}
            public int gethCondIma6(){ return this.hCondIma6;}
            public void sethCondIma6(int hCondIma6){this.hCondIma6=hCondIma6;}
            public int gethCondIma7(){ return this.hCondIma7;}
            public void sethCondIma7(int hCondIma7){this.hCondIma7=hCondIma7;}

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
