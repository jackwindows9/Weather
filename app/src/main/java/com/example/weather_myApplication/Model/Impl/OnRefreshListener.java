package com.example.weather_myApplication.Model.Impl;

import com.example.weather_myApplication.Model.Bean.JsonResponse;

/**
 * Created by 司维 on 2017/5/31.
 */

public interface OnRefreshListener {
    public void refreshFailed(String error);
    public void refreshSucceed(JsonResponse jsonResponse);
}
