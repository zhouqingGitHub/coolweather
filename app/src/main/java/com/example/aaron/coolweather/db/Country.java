package com.example.aaron.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by aaron on 2017/12/14.
 */

public class Country extends DataSupport {
    /*区县名称*/
    private String countyName;
    /*id*/
    private int    id;
    /*城市Id*/
    private int    cityId;

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    /*天气Id*/
    private String    weather_id;
    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }






}
