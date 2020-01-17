package com.example.model

import android.content.Context
import com.example.contract.WeatherContract
import com.example.net.ApiRetrofit
import com.example.reponse.BaseResponse
import com.example.reponse.WeatherResponse
import io.reactivex.Observable

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
class WeatherModel : WeatherContract.Model {
    override fun getWeather(adCode: String, context: Context): Observable<WeatherResponse> {
        return ApiRetrofit.getInstance(context).getWeather(adCode)
    }
}