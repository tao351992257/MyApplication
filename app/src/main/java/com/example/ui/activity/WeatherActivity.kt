package com.example.ui.activity

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
import com.example.myapplication.R
import com.example.presenter.WeatherPresenter
import com.example.reponse.WeatherResponse
import com.example.utils.Sha1Util
import com.example.utils.TextUtils.Companion.stringToJson
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity(), WeatherContract.View, AMapLocationListener {
    private var weatherPresenter: WeatherPresenter? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var oldAdcode: String? = null
    private var isFrist: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherPresenter = WeatherPresenter()
        weatherPresenter?.onCreate(this)
        val sHA1 = Sha1Util.sHA1(this)
        Log.d("TAG", "Sha1:$sHA1")
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
                if (isFrist) {
                    tvLocation.text = "${location.city}${location.district}"
                    weatherPresenter?.getWeather(location.adCode, this)
                    oldAdcode = location.adCode
                    isFrist = false
                } else {
                    if (!oldAdcode.equals(location.adCode)) {
                        weatherPresenter?.getWeather(location.adCode, this)
                    } else {
                        Log.d("TAG","newAdCode:${location.adCode}")
                    }
                }
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
