package com.example.net

import android.content.Context
import android.util.Log
import com.example.globle.AppConst
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/9
 */
open class BaseRetrofit constructor(context: Context) {


    val apiServiceGson: ApiService by lazy {
        Retrofit.Builder().baseUrl(AppConst.URL).client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
    }

    private val mOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(LogInterceptor())
                .build()
    }

    private inner class LogInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            Log.d("Log", "intercept(BaseRetrofit.kt:25)------>" + String.format("发起请求:%s on %s%n%s%s",
                    request.url(), chain.connection(), request.headers(), request.body().toString()))
            val response = chain.proceed(request)
            Log.d("Log", "intercept(BaseRetrofit.kt:29)------>" + String.format("接收响应:[%s] %n返回Json:[%s] %.1fms%n%s",
                    response.request().url(),
                    getResponseString(response),
                    response.headers()))
            return response
        }

    }

    private fun getResponseString(response: Response): String? {
        return try {
            val responseBody = response?.body()
            val source = responseBody?.source()
            source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source?.buffer()
            buffer?.clone()?.readString(Charset.forName("UTF-8"))
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        }
    }
}