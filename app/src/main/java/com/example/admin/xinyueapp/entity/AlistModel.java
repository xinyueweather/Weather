package com.example.admin.xinyueapp.entity;

import java.util.ArrayList;
import java.util.List;

public class AlistModel {
    public AlistModel() {
    }

    public List<Object> getData() {
        Alist alist = new Alist();
        alist.setCode(1000);
        alist.setMessage("Success");
        alist.setData(getAllData());
        return sortData(alist);
    }

    private List<Object> sortData(Alist alist) {
        List<Alist.MyDataList> arrays = alist.getData();
        List<Object> arrays_obj = new ArrayList<>();

        for (Alist.MyDataList array : arrays) {
            List<Alist.MyDataList.NowList> summ = array.getNowList();

            List<Alist.MyDataList.DaysList> days = array.getMyDaysList();
            if (summ != null && summ.size() > 0) {
                for (Alist.MyDataList.NowList sum : summ) {
                    arrays_obj.add(sum);
                }
            }
            if (days != null && days.size() > 0) {
                for (Alist.MyDataList.DaysList day : days) {
                    arrays_obj.add(day);
                }
            }
        }
        return arrays_obj;
    }

    private List<Alist.MyDataList.NowList> getSummaryData(int size) {
        List<Alist.MyDataList.NowList> summ = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Alist.MyDataList.NowList mySumm = new Alist.MyDataList.NowList();
            int a = i + 1;
            mySumm.setNowCondTxt("阴" + a);
            mySumm.setCurTem("1" + a + "℃");
            mySumm.setTolTem("10/15℃" + a);
            summ.add(mySumm);
        }
        return summ;
    }

    private List<Alist.MyDataList.DaysList> getDaysData(int size) {
        List<Alist.MyDataList.DaysList> day = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Alist.MyDataList.DaysList myDay = new Alist.MyDataList.DaysList();
            int a = i + 1;
            myDay.setDate("2018.12.11 " + a);
          //  myDay.setDaySta(R.drawable.orange);
            myDay.setDayTem("10/15℃" + a);
            day.add(myDay);
        }
        return day;
    }

    private List<Alist.MyDataList> getAllData() {
        List<Alist.MyDataList> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Alist.MyDataList myData = new Alist.MyDataList();
            myData.setMySummaryList(getSummaryData(i + 1));
            myData.setMyDaysList(getDaysData(i + 1));
            data.add(myData);
        }
        return data;
    }
}