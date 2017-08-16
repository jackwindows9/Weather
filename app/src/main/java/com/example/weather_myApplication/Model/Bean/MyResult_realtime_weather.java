package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/1.
 */

public class MyResult_realtime_weather {
    public String humidity;
    public String img;
    public String info;
    public String temperature;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
