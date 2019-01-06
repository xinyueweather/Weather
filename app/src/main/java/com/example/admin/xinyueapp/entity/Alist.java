package com.example.admin.xinyueapp.entity;


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
         private List<NowList> nowList;
         public void setMySummaryList(List<NowList> nowList){
             this.nowList=nowList;
         }
         public List<NowList> getNowList(){
             return this.nowList;
         }
        private List<DaysList> myDaysList;
        public void setMyDaysList(List<DaysList> daysList){
            this.myDaysList=daysList;
        }
        public List<DaysList> getMyDaysList(){
            return this.myDaysList;
        }

        public static class NowList{
            /**
             *  summaryList:[{"curTem":"6℃","curStatus":"阴","tolTem":"6℃/0℃"
             */
            private String nowTmp;
            private String nowCondTxt;
            private String tolTem;

            public String getCurTem(){return this.nowTmp;}
            public void setCurTem(String curTem){this.nowTmp=nowTmp;}

            public String getNowCondTxt(){return this.nowCondTxt;}
            public void setNowCondTxt(String curStatus){this.nowCondTxt=nowCondTxt;}

            public String getTolTem(){return this.tolTem;}
            public void setTolTem(String tolTem){this.tolTem=tolTem;}
        }
        public static class DaysList{
            /**
             *  date : 1月6日明天
             *  daySta : 阴
             *  dayTem  :  6 / 7
             */
            private String date;
           // private int daySta;
            private String dayTem;

            public String getDate(){return this.date;}
            public void setDate(String date){this.date=date;}

         //   public int getDaySta(){return this.daySta;}
         //   public void setDaySta(int daySta){this.daySta=daySta;}

            public String getDayTem(){return this.dayTem;}
            public void setDayTem(String dayTem){this.dayTem=dayTem;}

        }
    }
}
