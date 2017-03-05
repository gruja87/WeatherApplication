package com.example.dare.weather.remote;


import com.example.dare.weather.model.Weather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WeatherApi {

    @GET("v1/public/yql")
    Call<Weather> getWeather(@QueryMap(encoded = true)Map<String,String>params);
//                                @Query("format") String json,
//                                @Query("env") String store);

    @GET("data/2.5/weather")
    Call<Weather>getWeatherLocation(@Query("lat") double lat,
                                    @Query("lon") double lng,
                                    @Query("units")String units,
                                    @Query("APPID")String key);

}
