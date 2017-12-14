package com.example.aaron.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by aaron on 2017/12/14.
 */

public class City extends DataSupport {
    /*城市名称*/
    private String cityName;
    /*id*/
    private int    id;
    /*城市编码*/
    private int     cityCode;
    /*省份Id*/
    private int     privinceId;

    public int getPrivinceId() {
        return privinceId;
    }

    public void setPrivinceId(int privinceId) {
        this.privinceId = privinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    /*省份编码*/
    private int     provinceCode;
}
