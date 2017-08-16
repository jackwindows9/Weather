package com.example.weather_myApplication.Model.Impl;

import com.example.weather_myApplication.Model.Bean.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 司维 on 2017/6/1.
 */

public interface CityService {
    @GET("Query")
    Call<JsonResponse> getResponse(@Query("key") String key , @Query("cityname") String cityname);
}
