package com.example.aaron.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by aaron on 2017/12/14.
 */

public class Province extends DataSupport {
    /*省份名称*/
    private String provinceName;
    /*id*/
    private int    id;
    /*省份编码*/
    private int     provinceCode;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
