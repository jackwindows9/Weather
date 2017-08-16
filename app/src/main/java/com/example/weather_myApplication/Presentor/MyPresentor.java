package com.example.weather_myApplication.Presentor;

import com.example.weather_myApplication.Model.Bean.JsonResponse;
import com.example.weather_myApplication.Model.City;
import com.example.weather_myApplication.Model.Impl.OnRefreshListener;
import com.example.weather_myApplication.Model.WeatherImpl;
import com.example.weather_myApplication.View.ICityView;
import com.example.weather_myApplication.View.IMainView;
import com.example.weather_myApplication.View.ISearchView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 司维 on 2017/5/31.
 */

public class MyPresentor {
    public WeatherImpl weatherImpl;
    public ICityView iCityView;
    public IMainView iMainView;
    public ISearchView iSearchView;

    public MyPresentor(IMainView iMainView) {
        weatherImpl = new WeatherImpl();
        this.iMainView = iMainView;
    }

    public MyPresentor(ICityView iCityView) {
        this.iCityView = iCityView;
        weatherImpl = new WeatherImpl();
    }

    public MyPresentor(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
        weatherImpl = new WeatherImpl();
    }


    public void refresh(final int position) {
        weatherImpl.refresh(position, new OnRefreshListener() {
            @Override
            public void refreshFailed(String error) {
                iMainView.showError(error);
            }

            @Override
            public void refreshSucceed(JsonResponse jsonResponse) {
                try {
                    City city = new City();
                    String realtime_cityname = jsonResponse.getResult().getRealtime().getCity_name();
                    iMainView.setCityName(realtime_cityname, position);
                    String realtime_temperature = jsonResponse.getResult().getRealtime().getWeather().getTemperature();
                    iMainView.setRealtimeTemperature(realtime_temperature, position);

                    String realtime_weather = jsonResponse.getResult().getWeather()[0].getInfo().getDay()[1];
                    String realtime_weather_night = jsonResponse.getResult().getWeather()[0].getInfo().getNight()[1];
                    if (realtime_weather.equals(realtime_weather_night)) {
                        iMainView.setRealtimeWeather(realtime_weather, position);
                    } else {
                        realtime_weather = realtime_weather + "转" + realtime_weather_night;
                        iMainView.setRealtimeWeather(realtime_weather, position);
                    }

                    String maxinum = jsonResponse.getResult().getWeather()[0].getInfo().getDay()[2];
                    iMainView.setMaxinum(maxinum, position);

                    String mininum = jsonResponse.getResult().getWeather()[0].getInfo().getNight()[2];
                    iMainView.setMininum(mininum, position);

                    String wind_power = jsonResponse.getResult().getRealtime().getWind().getPower();
                    iMainView.setWindPower(wind_power, position);

                    String wind_direction = jsonResponse.getResult().getRealtime().getWind().getDirect();
                    iMainView.setWindDirection(wind_direction, position);

                    String humitity = jsonResponse.getResult().getRealtime().getWeather().getHumidity();
                    iMainView.setHumitity(humitity , position);
                    for (int i = 0; i < 7; i++) {
                        String date = jsonResponse.getResult().getWeather()[i].getDate().substring(5, 10);
                        String week = "周" + jsonResponse.getResult().getWeather()[i].getWeek();
                        maxinum = jsonResponse.getResult().getWeather()[i].getInfo().getDay()[2];
                        mininum = jsonResponse.getResult().getWeather()[i].getInfo().getNight()[2];
                        String day_weather = jsonResponse.getResult().getWeather()[i].getInfo().getDay()[1];
                        String night_weather = jsonResponse.getResult().getWeather()[i].getInfo().getNight()[1];
                        String weather = day_weather;
                        if (day_weather.equals(night_weather)) {
                            ;
                        } else {
                            weather = day_weather + "转" + night_weather;
                        }
                        switch (i) {
                            case 0:
                                iMainView.setDay0(date, week, weather, maxinum, mininum, position);
                                city.setDay0_date(date);
                                city.setDay0_week(week);
                                city.setDay0_weather(weather);
                                city.setDay0_maxinum(maxinum);
                                city.setDay0_mininum(mininum);
                                break;
                            case 1:
                                iMainView.setDay1(date, week, weather, maxinum, mininum, position);
                                city.setDay1_date(date);
                                city.setDay1_week(week);
                                city.setDay1_weather(weather);
                                city.setDay1_maxinum(maxinum);
                                city.setDay1_mininum(mininum);
                                break;
                            case 2:
                                iMainView.setDay2(date, week, weather, maxinum, mininum, position);
                                city.setDay2_date(date);
                                city.setDay2_week(week);
                                city.setDay2_weather(weather);
                                city.setDay2_maxinum(maxinum);
                                city.setDay2_mininum(mininum);
                                break;
                            case 3:
                                iMainView.setDay3(date, week, weather, maxinum, mininum, position);
                                city.setDay3_date(date);
                                city.setDay3_week(week);
                                city.setDay3_weather(weather);
                                city.setDay3_maxinum(maxinum);
                                city.setDay3_mininum(mininum);
                                break;
                            case 4:
                                iMainView.setDay4(date, week, weather, maxinum, mininum, position);
                                city.setDay4_date(date);
                                city.setDay4_week(week);
                                city.setDay4_weather(weather);
                                city.setDay4_maxinum(maxinum);
                                city.setDay4_mininum(mininum);
                                break;
                            case 5:
                                iMainView.setDay5(date, week, weather, maxinum, mininum, position);
                                city.setDay5_date(date);
                                city.setDay5_week(week);
                                city.setDay5_weather(weather);
                                city.setDay5_maxinum(maxinum);
                                city.setDay5_mininum(mininum);
                                break;
                            case 6:
                                iMainView.setDay6(date, week, weather, maxinum, mininum, position);
                                city.setDay6_date(date);
                                city.setDay6_week(week);
                                city.setDay6_weather(weather);
                                city.setDay6_maxinum(maxinum);
                                city.setDay6_mininum(mininum);
                                break;
                            default:
                                break;
                        }
                    }
                    city.setCity_name(realtime_cityname);
                    city.setTemperature(realtime_temperature);
                    city.setWeather(realtime_weather);
                    city.setWind_power(wind_power);
                    city.setWind_direction(wind_direction);
                    city.setHumitity(humitity);
                    List<City> list = new ArrayList<City>();
                    list = DataSupport.findAll(City.class);
                    if (list.size() == 0) {
                        ;
                    } else {
                        city.updateAll("city_name=?", list.get(position).getCity_name());
                    }
                } catch (Exception e) {
                    iMainView.showError("无法查询到该城市", position);
                }
            }
        });
    }

    public void getCity(String city) {
        weatherImpl.getCity(city, new OnRefreshListener() {
            @Override
            public void refreshFailed(String error) {
                iSearchView.showError(error);
            }

            @Override
            public void refreshSucceed(JsonResponse jsonResponse) {
                try {
                    City city = new City();
                    String city_name = jsonResponse.getResult().getRealtime().getCity_name();
                    String temperature = jsonResponse.getResult().getRealtime().getWeather().temperature;
                    String weather_day = jsonResponse.getResult().getWeather()[0].getInfo().getDay()[1];
                    String weather_night = jsonResponse.getResult().getWeather()[0].getInfo().getNight()[1];
                    if (!weather_day.equals(weather_night)) {
                        weather_day = weather_day + "转" + weather_night;
                    }
                    String wind_power = jsonResponse.getResult().getRealtime().getWind().getPower();
                    String wind_direction = jsonResponse.getResult().getRealtime().getWind().getDirect();
                    String humitity = jsonResponse.getResult().getRealtime().getWeather().getHumidity();
                    for (int i = 0; i < 7; i++) {
                        String date = jsonResponse.getResult().getWeather()[i].getDate().substring(5, 10);
                        String week = "周" + jsonResponse.getResult().getWeather()[i].getWeek();
                        String maxinum = jsonResponse.getResult().getWeather()[i].getInfo().getDay()[2];
                        String mininum = jsonResponse.getResult().getWeather()[i].getInfo().getNight()[2];
                        String day_weather = jsonResponse.getResult().getWeather()[i].getInfo().getDay()[1];
                        String night_weather = jsonResponse.getResult().getWeather()[i].getInfo().getNight()[1];
                        String weather = day_weather;
                        if (day_weather.equals(night_weather)) {
                            ;
                        } else {
                            weather = day_weather + "转" + night_weather;
                        }
                        switch (i) {
                            case 0:
                                city.setDay0_date(date);
                                city.setDay0_week(week);
                                city.setDay0_weather(weather);
                                city.setDay0_maxinum(maxinum);
                                city.setDay0_mininum(mininum);
                                break;
                            case 1:
                                city.setDay1_date(date);
                                city.setDay1_week(week);
                                city.setDay1_weather(weather);
                                city.setDay1_maxinum(maxinum);
                                city.setDay1_mininum(mininum);
                                break;
                            case 2:
                                city.setDay2_date(date);
                                city.setDay2_week(week);
                                city.setDay2_weather(weather);
                                city.setDay2_maxinum(maxinum);
                                city.setDay2_mininum(mininum);
                                break;
                            case 3:
                                city.setDay3_date(date);
                                city.setDay3_week(week);
                                city.setDay3_weather(weather);
                                city.setDay3_maxinum(maxinum);
                                city.setDay3_mininum(mininum);
                                break;
                            case 4:
                                city.setDay4_date(date);
                                city.setDay4_week(week);
                                city.setDay4_weather(weather);
                                city.setDay4_maxinum(maxinum);
                                city.setDay4_mininum(mininum);
                                break;
                            case 5:
                                city.setDay5_date(date);
                                city.setDay5_week(week);
                                city.setDay5_weather(weather);
                                city.setDay5_maxinum(maxinum);
                                city.setDay5_mininum(mininum);
                                break;
                            case 6:
                                city.setDay6_date(date);
                                city.setDay6_week(week);
                                city.setDay6_weather(weather);
                                city.setDay6_maxinum(maxinum);
                                city.setDay6_mininum(mininum);
                                break;
                            default:
                                break;
                        }
                    }
                    city.setCity_name(city_name);
                    city.setTemperature(temperature);
                    city.setWeather(weather_day);
                    city.setWind_power(wind_power);
                    city.setWind_direction(wind_direction);
                    city.setHumitity(humitity);
                    iSearchView.setCity();
                    iSearchView.getCityInfo(city);
                    iSearchView.changeClickState();
                } catch (Exception e) {
                    iSearchView.showError("无法查询到该城市");
                }
            }
        });
    }

    public void loadFromDatabases(int position) {
        List<City> list = new ArrayList<City>();
        list = DataSupport.findAll(City.class);
        if (list.size() != 0) {
            City city = list.get(position);
            iMainView.setCityName(city.getCity_name(), position);
            iMainView.setRealtimeWeather(city.getWeather(), position);
            iMainView.setRealtimeTemperature(city.getTemperature(), position);
            iMainView.setWindPower(city.getWind_power(), position);
            iMainView.setWindDirection(city.getWind_direction(), position);
            iMainView.setMaxinum(city.getDay0_maxinum(), position);
            iMainView.setMininum(city.getDay0_mininum(), position);
            iMainView.setHumitity(city.getHumitity(), position);
            for (int i = 0; i < 7; i++) {
                switch (i) {
                    case 0:
                        iMainView.setDay0(city.getDay0_date(), city.getDay0_week(), city.getDay0_weather(), city.getDay0_maxinum(), city.getDay0_mininum(), position);
                        break;
                    case 1:
                        iMainView.setDay1(city.getDay1_date(), city.getDay1_week(), city.getDay1_weather(), city.getDay1_maxinum(), city.getDay1_mininum(), position);
                        break;
                    case 2:
                        iMainView.setDay2(city.getDay2_date(), city.getDay2_week(), city.getDay2_weather(), city.getDay2_maxinum(), city.getDay2_mininum(), position);
                        break;
                    case 3:
                        iMainView.setDay3(city.getDay3_date(), city.getDay3_week(), city.getDay3_weather(), city.getDay3_maxinum(), city.getDay3_mininum(), position);
                        break;
                    case 4:
                        iMainView.setDay4(city.getDay4_date(), city.getDay4_week(), city.getDay4_weather(), city.getDay4_maxinum(), city.getDay4_mininum(), position);
                        break;
                    case 5:
                        iMainView.setDay5(city.getDay5_date(), city.getDay5_week(), city.getDay5_weather(), city.getDay5_maxinum(), city.getDay5_mininum(), position);
                        break;
                    case 6:
                        iMainView.setDay6(city.getDay6_date(), city.getDay6_week(), city.getDay6_weather(), city.getDay6_maxinum(), city.getDay6_mininum(), position);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void refreshDatabases(List<City> list) {
        if (list.size() == 0) {
            ;//do nothing
        } else {
            for (int i = 0; i < list.size(); i++) {
                weatherImpl.getCity(list.get(i).getCity_name(), new OnRefreshListener() {
                    @Override
                    public void refreshFailed(String error) {
                        iMainView.showError(error);
                    }

                    @Override
                    public void refreshSucceed(JsonResponse jsonResponse) {
                        City city = new City();
                        String city_name = jsonResponse.getResult().getRealtime().getCity_name();
                        String temperature = jsonResponse.getResult().getRealtime().getWeather().temperature;
                        String weather_day = jsonResponse.getResult().getWeather()[0].getInfo().getDay()[1];
                        String weather_night = jsonResponse.getResult().getWeather()[0].getInfo().getNight()[1];
                        if (!weather_day.equals(weather_night)) {
                            weather_day = weather_day + "转" + weather_night;
                        }
                        String wind_power = jsonResponse.getResult().getRealtime().getWind().getPower();
                        String wind_direction = jsonResponse.getResult().getRealtime().getWind().getDirect();
                        String humitity = jsonResponse.getResult().getRealtime().getWeather().getHumidity();
                        for (int i = 0; i < 7; i++) {
                            String date = jsonResponse.getResult().getWeather()[i].getDate().substring(5, 10);
                            String week = "周" + jsonResponse.getResult().getWeather()[i].getWeek();
                            String maxinum = jsonResponse.getResult().getWeather()[i].getInfo().getDay()[2];
                            String mininum = jsonResponse.getResult().getWeather()[i].getInfo().getNight()[2];
                            String day_weather = jsonResponse.getResult().getWeather()[i].getInfo().getDay()[1];
                            String night_weather = jsonResponse.getResult().getWeather()[i].getInfo().getNight()[1];
                            String weather = day_weather;
                            if (day_weather.equals(night_weather)) {
                                ;
                            } else {
                                weather = day_weather + "转" + night_weather;
                            }
                            switch (i) {
                                case 0:
                                    city.setDay0_date(date);
                                    city.setDay0_week(week);
                                    city.setDay0_weather(weather);
                                    city.setDay0_maxinum(maxinum);
                                    city.setDay0_mininum(mininum);
                                    break;
                                case 1:
                                    city.setDay1_date(date);
                                    city.setDay1_week(week);
                                    city.setDay1_weather(weather);
                                    city.setDay1_maxinum(maxinum);
                                    city.setDay1_mininum(mininum);
                                    break;
                                case 2:
                                    city.setDay2_date(date);
                                    city.setDay2_week(week);
                                    city.setDay2_weather(weather);
                                    city.setDay2_maxinum(maxinum);
                                    city.setDay2_mininum(mininum);
                                    break;
                                case 3:
                                    city.setDay3_date(date);
                                    city.setDay3_week(week);
                                    city.setDay3_weather(weather);
                                    city.setDay3_maxinum(maxinum);
                                    city.setDay3_mininum(mininum);
                                    break;
                                case 4:
                                    city.setDay4_date(date);
                                    city.setDay4_week(week);
                                    city.setDay4_weather(weather);
                                    city.setDay4_maxinum(maxinum);
                                    city.setDay4_mininum(mininum);
                                    break;
                                case 5:
                                    city.setDay5_date(date);
                                    city.setDay5_week(week);
                                    city.setDay5_weather(weather);
                                    city.setDay5_maxinum(maxinum);
                                    city.setDay5_mininum(mininum);
                                    break;
                                case 6:
                                    city.setDay6_date(date);
                                    city.setDay6_week(week);
                                    city.setDay6_weather(weather);
                                    city.setDay6_maxinum(maxinum);
                                    city.setDay6_mininum(mininum);
                                    break;
                                default:
                                    break;
                            }
                        }
                        city.setCity_name(city_name);
                        city.setTemperature(temperature);
                        city.setWeather(weather_day);
                        city.setWind_power(wind_power);
                        city.setWind_direction(wind_direction);
                        city.setHumitity(humitity);
                        city.updateAll("city_name = ?", city.getCity_name());
                    }
                });
            }
        }
    }
}
