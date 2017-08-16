package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/1.
 */

public class MyResult_realtime {
    public MyResult_realtime_wind wind;
    public String time;
    public MyResult_realtime_weather weather;
    public String dataUptime;
    public String date;
    public String city_code;
    public String city_name;
    public String week;
    public String moon;

    public MyResult_realtime_wind getWind() {
        return wind;
    }

    public void setWind(MyResult_realtime_wind wind) {
        this.wind = wind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MyResult_realtime_weather getWeather() {
        return weather;
    }

    public void setWeather(MyResult_realtime_weather weather) {
        this.weather = weather;
    }

    public String getDataUptime() {
        return dataUptime;
    }

    public void setDataUptime(String dataUptime) {
        this.dataUptime = dataUptime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }
}
