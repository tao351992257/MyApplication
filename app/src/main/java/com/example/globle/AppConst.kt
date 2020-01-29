package com.example.globle

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/3
 */
object AppConst {
    const val APP_TAG = "MyApplication"
    const val URL = "https://restapi.amap.com/v3/"
    const val NET_STATUS_OK = 200
    const val API_KEY = "f4181ce5e9f1faf6c19cb6f04a2a3cab"
    @JvmStatic
    fun ServerUrl(adCode: String): String {
        return "${URL}weather/weatherInfo?city=$adCode&key=$API_KEY&extensions=all"
    }
}