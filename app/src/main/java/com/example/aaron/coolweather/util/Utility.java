package com.example.aaron.coolweather.util;

import android.text.TextUtils;

import com.example.aaron.coolweather.db.City;
import com.example.aaron.coolweather.db.Country;
import com.example.aaron.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aaron on 2017/12/14.
 * JSon 数据处理类
 */

public class Utility {
    public static boolean handleProvinceReponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject proviceObj = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceCode(proviceObj.getInt("id"));
                    province.setProvinceName(proviceObj.getString("name"));
                    province.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        return false;
    }

    public static boolean handleCityReponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject proviceObj = allProvinces.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(proviceObj.getInt("id"));
                    city.setCityName(proviceObj.getString("name"));
                    city.setPrivinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        return false;
    }

    /*
    * */
    public static boolean handleCountryReponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject proviceObj = allProvinces.getJSONObject(i);
                    Country country = new Country();
                    country.setCountyName(proviceObj.getString("name"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        return false;
    }
}
