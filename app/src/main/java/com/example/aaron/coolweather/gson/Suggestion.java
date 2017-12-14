package com.example.aaron.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaron on 2017/12/14.
 */

public class Suggestion {
    @SerializedName("comf")
    public  comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;
    public Sport sport;
    public  class comfort
    {
        @SerializedName("txt")
        public String info;
    }
    public  class  Sport
    {
        @SerializedName("txt")
        public String info;
    }
    public class  CarWash
    {
        @SerializedName("txt")
        public String info;
    }
}
