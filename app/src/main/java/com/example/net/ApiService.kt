package com.example.net

import com.example.globle.AppConst.API_KEY
import com.example.reponse.BaseResponse
import com.example.reponse.WeatherResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/14
 */
interface ApiService {
    @GET("weather/weatherInfo?key=$API_KEY&extensions=all&")
    fun getWeather(@Query("city") adCode: String): Observable<WeatherResponse>

    @GET("weather/weatherInfo?key=$API_KEY&extensions=all?")
    fun Request(@Query("city") adCode: String): Call<BaseResponse>
}