package com.example.weather_myApplication.View;

import com.example.weather_myApplication.Model.City;

/**
 * Created by 司维 on 2017/6/5.
 */

public interface ISearchView {
    public void setCity();
    public void getCityInfo(City city);
    public void showError(String error);
    public void changeClickState();//改变搜索结果的点击状态和显示状态
}
