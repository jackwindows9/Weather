package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/1.
 */

public class MyResult {
    public MyResult_realtime realtime;
    public MyResult_life life;
    public MyResult_weather[] weather=new MyResult_weather[7];
    public MyResult_pm25 pm25;
    public Object date;
    public int isForeign;

    public MyResult_weather[] getWeather() {
        return weather;
    }

    public void setWeather(MyResult_weather[] weather) {
        this.weather = weather;
    }

    public MyResult_realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(MyResult_realtime realtime) {
        this.realtime = realtime;
    }

    public MyResult_life getLife() {
        return life;
    }

    public void setLife(MyResult_life life) {
        this.life = life;
    }


    public MyResult_pm25 getPm25() {
        return pm25;
    }

    public void setPm25(MyResult_pm25 pm25) {
        this.pm25 = pm25;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public int getIsForeign() {
        return isForeign;
    }

    public void setIsForeign(int isForeign) {
        this.isForeign = isForeign;
    }
}
