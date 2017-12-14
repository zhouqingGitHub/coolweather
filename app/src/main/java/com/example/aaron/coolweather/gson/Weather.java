package com.example.aaron.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aaron on 2017/12/14.
 */

public class Weather {
   public String status;
   public Basic basic;
   public AQI aqi;
   public Now now;
   public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<DailyForecast> forecastList;
}
