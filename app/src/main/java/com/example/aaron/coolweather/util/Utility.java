package com.example.aaron.coolweather.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.example.aaron.coolweather.db.City;
import com.example.aaron.coolweather.db.Country;
import com.example.aaron.coolweather.db.Province;
import com.example.aaron.coolweather.gson.Weather;
import com.google.gson.Gson;

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

                    country.setWeather_id(proviceObj.getString("weather_id"));
                    country.setCityId(cityId);
                    /*
                    * 保存到数据库中
                    * */
                    country.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        return false;
    }
    public static Weather handleWeatherResponse(String response)
    {
        if (!TextUtils.isEmpty(response))
        {
            try {
                JSONObject obj =  new JSONObject(response);
                JSONArray  jsonArray = obj.getJSONArray("HeWeather");
                String weatherContent = jsonArray.getJSONObject(0).toString();
                return new Gson().fromJson(weatherContent,Weather.class);


            }catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return null;
    }
    public static void  setFullScreen(Activity context)
    {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = context.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
