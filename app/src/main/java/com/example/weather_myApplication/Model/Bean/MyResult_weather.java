package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/1.
 */

public class MyResult_weather {
    public String date;
    public String week;
    public String longli;
    public MyResult_weather_info info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLongli() {
        return longli;
    }

    public void setLongli(String longli) {
        this.longli = longli;
    }

    public MyResult_weather_info getInfo() {
        return info;
    }

    public void setInfo(MyResult_weather_info info) {
        this.info = info;
    }
}
