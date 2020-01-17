package com.example.net

import android.content.Context
import com.example.reponse.BaseResponse
import com.example.reponse.WeatherResponse
import io.reactivex.Observable
import retrofit2.Call

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
class ApiRetrofit constructor(context: Context) : BaseRetrofit(context) {
    companion object {
        private var apiRetrofit: ApiRetrofit? = null
        fun getInstance(context: Context) = apiRetrofit ?: synchronized(this) {
            apiRetrofit ?: ApiRetrofit(context.applicationContext).also { apiRetrofit = it }
        }
    }

    fun getWeather(adCode: String): Observable<WeatherResponse> {
        return apiServiceGson.getWeather(adCode)
    }

    fun Request(adCode:String):Call<BaseResponse>{
        return apiServiceGson.Request(adCode)
    }
}