package com.example.aaron.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aaron.coolweather.gson.DailyForecast;
import com.example.aaron.coolweather.gson.Weather;
import com.example.aaron.coolweather.util.HttpUtil;
import com.example.aaron.coolweather.util.Utility;

import java.io.IOException;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by aaron on 2017/12/14.
 */

public class WeatherActiity extends AppCompatActivity {

    /**
     * 地点(标题)
     */
    private TextView title_txt;
    /**
     * 时间
     */
    private TextView updateTime_txt;
    /**
     * 当前温度
     */
    private TextView temperature_txt;
    /**
     * 天气情况
     */
    private TextView weatherInfo_txt;

    /**
     * AQI指数
     */
    private TextView aqi_txt;
    /**
     * PM2.5指数
     */
    private TextView pm_txt;
    /**
     * 舒适度
     */
    private TextView comfor_txt;
    /**
     * 洗车
     */
    private TextView carWash_txt;
    /**
     * 运动建议
     */
    private TextView sportAdvice_txt;

    /**
     * Home按钮
     */
    private Button home_btn;

    /**
     * 天气预报
     */
    private LinearLayout forecast_linerLayut;
    private ImageView   bingYingImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_weather);
        initView();
        Utility.setFullScreen(this);
        String weatherId = getIntent().getStringExtra("weatherId");
        queryWeather(weatherId);
        /*加载背景图片*/
        loadBingPic();
    }

    /**
     * 初始化界面
     */
    private void  initView()
    {
        title_txt = findViewById(R.id.city_txt);
        updateTime_txt = findViewById(R.id.updateTimeTitle_txt);
        temperature_txt = findViewById(R.id.degree_text);
        weatherInfo_txt = findViewById(R.id.weather_info_text);
        aqi_txt = findViewById(R.id.api_txt);
        pm_txt  = findViewById(R.id.pm2_5_txt);
        comfor_txt = findViewById(R.id.comfort_text);
        carWash_txt = findViewById(R.id.car_wash_text);
        sportAdvice_txt = findViewById(R.id.sport_text);
        home_btn = findViewById(R.id.btnBack);
        forecast_linerLayut = findViewById(R.id.forecastLayout);
        bingYingImageView = findViewById(R.id.bing_pic_img);

    }
    /**
     * 根据weatherID 获取天气情况
     */
    private void queryWeather(final String  weatherId)
    {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=2de7f086d5e743f384fac56bac8687dd";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseTxt = response.body().string();

                final   Weather weather = Utility.handleWeatherResponse(responseTxt);
                if (weather != null && weather.status.equals("ok")) {
                    /*回到主函数*/
                    runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           showWeatherInfo(weather);

                       }
                   });
                }
            }
        });

    }
    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActiity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActiity.this).load(bingPic).into(bingYingImageView);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 显示页面信息
     */
    private  void  showWeatherInfo(Weather weather)
    {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        title_txt.setText(cityName);
        updateTime_txt.setText(updateTime);
        temperature_txt.setText(degree);
        weatherInfo_txt.setText(weatherInfo);

        aqi_txt.setText(weather.aqi.city.aqi);
        pm_txt.setText(weather.aqi.city.pm25);

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;

        carWash_txt.setText(carWash);
        sportAdvice_txt.setText(sport);
        comfor_txt.setText(comfort);


        /*天气预报信息*/
        for (DailyForecast forecast:
             weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecaset_item,forecast_linerLayut,false);
            TextView dateTV =  view.findViewById(R.id.updateeTime_txt);
            TextView statusTV =  view.findViewById(R.id.weatherStatus_txt);
            TextView tmp_max =  view.findViewById(R.id.temperature_max);
            TextView tmp_mix =  view.findViewById(R.id.temperature_min);

            dateTV.setText(forecast.date);
            statusTV.setText(forecast.more.info );
            tmp_max.setText(forecast.temperature.max);
            tmp_mix.setText(forecast.temperature.min);
            forecast_linerLayut.addView(view);

        }
    }

}
