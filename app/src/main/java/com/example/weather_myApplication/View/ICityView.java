package com.example.weather_myApplication.View;

import com.example.weather_myApplication.Model.City;

import java.util.List;

/**
 * Created by 司维 on 2017/5/31.
 */

public interface ICityView {
    public void refreshDatabase(List<City> list);
}
