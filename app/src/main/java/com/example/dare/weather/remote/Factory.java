package com.example.dare.weather.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Factory {

    private static WeatherApi service;

    public static WeatherApi getInstance(){
        if (service==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(WeatherApi.class);
            return service;
        }
        else {
            return service;
        }
    }
}
