package com.example.aaron.coolweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaron.coolweather.db.City;
import com.example.aaron.coolweather.db.Country;
import com.example.aaron.coolweather.db.Province;
import com.example.aaron.coolweather.gson.Weather;
import com.example.aaron.coolweather.util.HttpUtil;
import com.example.aaron.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
*/
public class choose_area_frame extends Fragment {


    private TextView titleView;
    private Button   backBtn;
    private ListView listView;
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_City = 1;
    private static final int LEVEL_COUNTRY = 2;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<String>();
    /**
     * 省列表
     */
    private List<Province>provincesList;
    /**
     * 市列表
     */
    private List<City>cityList;
    /**
     * 县列表
     */
    private List<Country>countryList;
    /**
     * 选中的省份
     */
    private Province     selectedProvince;
    /**
     *  选中的城市
     */
    private City         selectdCity;
   /**
    * 当前选中的级别
    */
    private int    currentLevel;

    private static final String JSON_URL_PREFIX = "http://guolin.tech/api/china";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.choose_area,container,false);
       titleView = view.findViewById(R.id.title_txt);
       backBtn = view.findViewById(R.id.btnBack);
       listView = view.findViewById(R.id.arealist);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        currentLevel = LEVEL_PROVINCE;
        return view;
    }

    /**
     *ListView 和 BackBtn添加点击事件
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvince();
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provincesList.get(i);
                    queryCitys();
                }
                else  if (currentLevel == LEVEL_City)
                {
                    selectdCity = cityList.get(i);
                    queryCountry();
                }
                else
                {
                    String weatherId = countryList.get(i).getWeather_id();
                    Intent inent = new Intent(getActivity(), WeatherActiity.class);
                    inent.putExtra("weatherId",weatherId);
                    startActivity(inent);

                }
            }
        });
         backBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (currentLevel == LEVEL_COUNTRY)
                      queryCitys();
                 else if(currentLevel == LEVEL_City)
                     queryProvince();
             }
         });
    }

    /**
     * 查询所有的省份 优先从数据库中查询、
     * 如果没有从网络上获取
     */
    private void  queryProvince()
    {
        titleView.setText("中国");
        backBtn.setVisibility(View.GONE);
        provincesList = DataSupport.findAll(Province.class);
        if (provincesList.size() > 0) {
            dataList.clear();
            for (Province province:
                 provincesList) {
                dataList.add(province.getProvinceName());
                adapter.notifyDataSetChanged();
                currentLevel = LEVEL_PROVINCE;
            }
        }
        else
        {
            queryFromServer(JSON_URL_PREFIX,LEVEL_PROVINCE);
        }
    }
    /**
     * 查询所有的城市 优先从数据库中获取
     */
    private void queryCitys()
    {
        titleView.setText(selectedProvince.getProvinceName());
        backBtn.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("privinceId=?",String.valueOf(selectedProvince.getProvinceCode())).find(City.class);
        if (cityList.size() >0)
        {
            dataList.clear();
            for (City city:
                    cityList) {
                dataList.add(city.getCityName());
                adapter.notifyDataSetChanged();
                currentLevel = LEVEL_City;

            }
        }
        else
        {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = JSON_URL_PREFIX +"/"+ provinceCode;
            queryFromServer(address,LEVEL_City);

        }


    }
    /**
     * 查询所有的县 优先从数据库中获取
     */
    private void  queryCountry()
    {
        titleView.setText(selectdCity.getCityName());
        backBtn.setVisibility(View.VISIBLE);
        countryList = DataSupport.where("cityId = ?",String.valueOf(selectdCity.getCityCode())).find(Country.class);

        if (countryList.size() >0)
        {
            dataList.clear();
            for (Country country:
                    countryList){
                dataList.add(country.getCountyName());
                adapter.notifyDataSetChanged();
                currentLevel = LEVEL_COUNTRY;

            }
        }
        else
        {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectdCity.getCityCode();
            String address = JSON_URL_PREFIX +"/" + provinceCode + "/" + cityCode;
            queryFromServer(address,LEVEL_COUNTRY);
        }
    }

    /**
     * 从网络上获取数据
     */
    private void queryFromServer(String url, final int type)
    {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Toast.makeText(getContext(),"网络请求失败",Toast.LENGTH_LONG);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean result = false;
                String responseText = response.body().string();

                if (type == LEVEL_PROVINCE) {
                    result = Utility.handleProvinceReponse(responseText);
                }
                else  if (type == LEVEL_City)
                {
                    result = Utility.handleCityReponse(responseText,selectedProvince.getProvinceCode());
                }
                else
                    result =  Utility.handleCountryReponse(responseText,selectdCity.getCityCode());
                if (result)
                {

                    /*切换到主线程*/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type == LEVEL_PROVINCE)
                                queryProvince();
                            else if (type == LEVEL_City)
                                queryCitys();
                            else
                                queryCountry();
                        }

                    });
                }
            }
        });

    }
}
