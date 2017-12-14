package com.example.aaron.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaron on 2017/12/14.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public MoreTmp more;

    public class MoreTmp {

        @SerializedName("txt")
        public String info;

    }
}
