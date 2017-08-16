package com.example.weather_myApplication.Model.Bean;

/**
 * Created by 司维 on 2017/6/1.
 */

public class MyResult_realtime_wind {
    public Object windspeed;
    public String direct;
    public String power;
    public Object offset;

    public Object getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Object windspeed) {
        this.windspeed = windspeed;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public Object getOffset() {
        return offset;
    }

    public void setOffset(Object offset) {
        this.offset = offset;
    }
}
