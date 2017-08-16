package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/2.
 */

/**
 * Invalid data,it may be null.
 */
public class MyResult_pm25 {

    public String key;
    public String show_desc;
    public MyResult_pm25_pm25 pm25;
    public String dateTime;
    public String cityname;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShow_desc() {
        return show_desc;
    }

    public void setShow_desc(String show_desc) {
        this.show_desc = show_desc;
    }

    public MyResult_pm25_pm25 getPm25() {
        return pm25;
    }

    public void setPm25(MyResult_pm25_pm25 pm25) {
        this.pm25 = pm25;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
