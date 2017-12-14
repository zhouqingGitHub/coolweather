package com.example.aaron.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaron on 2017/12/14.
 */

public class DailyForecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature
    {
        public String max;
        public String min;
    }

    public class More
    {
        @SerializedName("txt_d")

        public String info;
    }

}
