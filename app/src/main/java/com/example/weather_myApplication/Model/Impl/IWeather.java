package com.example.weather_myApplication.Model.Impl;

import com.example.weather_myApplication.Model.City;

import java.util.List;

/**
 * Created by 司维 on 2017/5/31.
 */

public interface IWeather {
    public void refresh(int position,OnRefreshListener onRefreshListener);
    public void getCity(String city,OnRefreshListener onRefreshListener);
    public void refresh(List<City> list,OnRefreshListener onRefreshListener);
}
