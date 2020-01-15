package com.example.net

import retrofit2.Call
import retrofit2.http.GET

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/14
 */
interface ApiService {
    @GET
    fun getWeather(url: String): Call<String>
}