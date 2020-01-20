package com.example.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.contract.WeatherContract
import com.example.exception.AppError
import com.example.globle.AppConst
import com.example.myapplication.R
import com.example.presenter.WeatherPresenter
import com.example.reponse.WeatherResponse
import com.example.utils.KeyboardUtil
import com.example.utils.TextUtils.Companion.stringToJson
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weather.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherActivity : AppCompatActivity(), WeatherContract.View, AMapLocationListener {
    private var weatherPresenter: WeatherPresenter? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherPresenter = WeatherPresenter()
        weatherPresenter?.onCreate(this)
        initLocation()
    }

    private fun initLocation() {
        mLocationOption = AMapLocationClientOption()
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption?.interval = 2000
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient?.setLocationOption(mLocationOption)
        mLocationClient?.setLocationListener(this)
    }

    override fun onStart() {
        super.onStart()
        mLocationClient?.startLocation()
    }

    override fun showWeather(response: WeatherResponse) {
        val toJson = Gson().toJson(response)
        tvWeather.text = stringToJson(toJson)
    }

    override fun showError(appError: AppError) {
        Toast.makeText(this, appError.getMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherPresenter?.onDestory()
        mLocationClient?.stopLocation()
    }

    override fun onLocationChanged(location: AMapLocation?) {
        if (location != null) {
            if (location.errorCode == 0) {
                weatherPresenter?.getWeather(location.adCode, this)
            } else {
                Log.e("Error", "location Error, ErrCode:"
                        + location.errorCode + ", errInfo:"
                        + location.errorInfo)
            }
        } else {
            Log.d("Log", "onLocationChanged(WeatherActivity.kt:78)------>Location is null")
        }
    }
}
