package com.example.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
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
import com.example.utils.QMUIDialog
import com.example.utils.StatusBarUtil
import com.example.utils.TextUtils.Companion.stringToJson
import com.google.gson.Gson
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.QMUIEmptyView
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity(), WeatherContract.View, AMapLocationListener {
    private var weatherPresenter: WeatherPresenter? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var oldAdcode: String? = null
    private var isFirst: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        setContentView(R.layout.activity_weather)
        QMUIStatusBarHelper.translucent(this,resources.getColor(R.color.colorPrimary))
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

    override fun showWeather(location: AMapLocation, response: WeatherResponse) {

    }

    override fun showError(appError: AppError) {

    }

    override fun onDestroy() {
        super.onDestroy()
        weatherPresenter?.onDestory()
        mLocationClient?.stopLocation()
    }

    override fun onLocationChanged(location: AMapLocation?) {
        if (location != null) {
            if (location.errorCode == 0) {
                if (isFirst) {
                    weatherPresenter?.getWeather(location, this)
                    oldAdcode = location.adCode
                    isFirst = false
                } else {
                    if (!oldAdcode.equals(location.adCode)) {
                        oldAdcode = location.adCode
                        weatherPresenter?.getWeather(location, this)
                    }
                }
            } else {
                Log.e(AppConst.APP_TAG, "location Error, ErrCode:"
                        + location.errorCode + ", errInfo:"
                        + location.errorInfo)
            }
        } else {
            Log.d(AppConst.APP_TAG, "onLocationChanged(WeatherActivity.kt:78)------>Location is null")
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
