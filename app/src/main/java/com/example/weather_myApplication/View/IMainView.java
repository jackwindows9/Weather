package com.example.weather_myApplication.View;

/**
 * Created by 司维 on 2017/5/31.
 */

public interface IMainView {
    public void setCityName(String text,int position);
    public void setRealtimeWeather(String text,int position);
    public void setRealtimeTemperature(String text,int position);
    public void setMaxinum(String text,int position);
    public void setMininum(String text,int position);
    public void setWindPower(String text,int position);
    public void setWindDirection(String text,int position);
    public void setHumitity(String text,int position);
    public void showError(String error,int position);
    public void showError(String error);

    public void setDay0(String date,String week,String weather,String maxinum,String mininum,int position);
    public void setDay1(String date,String week,String weather,String maxinum,String mininum,int position);
    public void setDay2(String date,String week,String weather,String maxinum,String mininum,int position);
    public void setDay3(String date,String week,String weather,String maxinum,String mininum,int position);
    public void setDay4(String date,String week,String weather,String maxinum,String mininum,int position);
    public void setDay5(String date,String week,String weather,String maxinum,String mininum,int position);
    public void setDay6(String date,String week,String weather,String maxinum,String mininum,int position);
}
