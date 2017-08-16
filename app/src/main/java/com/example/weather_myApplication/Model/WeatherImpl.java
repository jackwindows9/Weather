package com.example.weather_myApplication.Model;

import com.example.weather_myApplication.Model.Bean.JsonResponse;
import com.example.weather_myApplication.Model.Impl.CityService;
import com.example.weather_myApplication.Model.Impl.IWeather;
import com.example.weather_myApplication.Model.Impl.OnRefreshListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 司维 on 2017/5/31.
 */

public class WeatherImpl implements IWeather{
    private String MyRequest="http://api.avatardata.cn/Weather/";
    private String City;
    @Override
    public void refresh(int position,final OnRefreshListener onRefreshListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.avatardata.cn/Weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        List<City> list=new ArrayList<City>();
        list= DataSupport.findAll(City.class);
        String city;
        if(list.size()==0){
            city="江宁";
        }
        else{
            city=list.get(position).getCity_name();
        }
        CityService cityService=retrofit.create(CityService.class);
        Call<JsonResponse> call = cityService.getResponse("c38b8d4d6ad845a1ae8e7a8e11898208",city);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                onRefreshListener.refreshSucceed(response.body());
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                onRefreshListener.refreshFailed("请检查网络连接");
            }
        });
    }

    @Override
    public void getCity(String city,final OnRefreshListener onRefreshListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.avatardata.cn/Weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CityService cityService=retrofit.create(CityService.class);
        Call<JsonResponse> call = cityService.getResponse("c38b8d4d6ad845a1ae8e7a8e11898208",city);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                onRefreshListener.refreshSucceed(response.body());
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                onRefreshListener.refreshFailed("请检查网络连接");
            }
        });
    }

    @Override
    public void refresh(List<City> list, OnRefreshListener onRefreshListener) {

    }
}
