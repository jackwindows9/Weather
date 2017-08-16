package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/1.
 */

public class MyResult_weather_info {
    public Object dawn;
    public String[] day=new String[6];
    public String[] night=new String[6];

    public Object getDawn() {
        return dawn;
    }

    public void setDawn(Object dawn) {
        this.dawn = dawn;
    }

    public String[] getDay() {
        return day;
    }

    public void setDay(String[] day) {
        this.day = day;
    }

    public String[] getNight() {
        return night;
    }

    public void setNight(String[] night) {
        this.night = night;
    }
}
