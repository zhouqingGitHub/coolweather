package com.example.aaron.coolweather.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by aaron on 2017/12/14.
 * 网络请求处理类
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        /*创建请求*/
        Request request = new Request.Builder().url(url).build();
        /*发送请求*/
        client.newCall(request).enqueue(callback);
    }
}
