package com.example.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.example.contract.WeatherContract
import com.example.exception.AppError
import com.example.globle.AppConst
import com.example.myapplication.R
import com.example.presenter.WeatherPresenter
import com.example.reponse.WeatherResponse
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity(), WeatherContract.View, AMapLocationListener {
    private var weatherPresenter: WeatherPresenter? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var aMap: AMap? = null
    private var oldAdCode: String? = null
    private var isFirst: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        setContentView(R.layout.activity_weather)
        if (aMap == null) {
            aMap = map_view.map
        }
        map_view.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this, resources.getColor(R.color.colorPrimary))
        weatherPresenter = WeatherPresenter()
        weatherPresenter?.onCreate(this)
        initLocation()
        initListener()

    }

    private fun initListener() {
        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.setBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })
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


    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        map_view.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        map_view.onSaveInstanceState(outState)
    }


    override fun showWeather(location: AMapLocation, response: WeatherResponse) {

    }

    override fun showError(appError: AppError) {

    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        map_view.onDestroy()
        weatherPresenter?.onDestory()
        mLocationClient?.stopLocation()
    }

    override fun onLocationChanged(location: AMapLocation?) {
        if (location != null) {
            if (location.errorCode == 0) {
                if (isFirst) {
                    weatherPresenter?.getWeather(location, this)
                    oldAdCode = location.adCode
                    isFirst = false
                } else {
                    if (!oldAdCode.equals(location.adCode)) {
                        oldAdCode = location.adCode
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
