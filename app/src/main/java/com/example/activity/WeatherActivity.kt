package com.example.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.example.contract.WeatherContract
import com.example.exception.AppError
import com.example.globle.AppConst
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherPresenter = WeatherPresenter()
        weatherPresenter?.onCreate(this)
        initLocation()
        init()
    }

    private fun initLocation() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient?.setLocationListener(this)
    }

    private fun init() {
        btnRequest.setOnClickListener {
            weatherPresenter?.getWeather(etAdcode.text.trim().toString(), this)
        }
    }

    @Synchronized
    private fun loadData(adCode: String) {
        val url = URL(AppConst.ServerUrl(adCode))
        var httpURLConnection = url.openConnection() as HttpURLConnection
        if (httpURLConnection.responseCode == AppConst.NET_STATUS_OK) {
            httpURLConnection.connectTimeout = 1000
            val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.inputStream, "UTF-8"))
            var readLine: String?
            while (bufferedReader.readLine().also { readLine = it } != null) {
                readLine?.let {
                    runOnUiThread { tvGson.text = stringToJson(it) }
                }
            }
        }
    }

    override fun showWeather(response: WeatherResponse) {
        KeyboardUtil.hideKeyboard(etAdcode)
        val toJson = Gson().toJson(response)
        tvGson.text = stringToJson(toJson)
    }

    override fun showError(appError: AppError) {
        Toast.makeText(this, appError.getMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherPresenter?.onDestory()
    }

    override fun onLocationChanged(location: AMapLocation?) {

    }
}
