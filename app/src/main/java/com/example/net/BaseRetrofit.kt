package com.example.net

import android.content.Context
import android.util.Log
import com.example.globle.AppConst
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
    }

    private val mOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(HttpLoggingInterceptor())
                .addInterceptor(LogInterceptor())
                .build()

    }

    fun HttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            val decode = URLDecoder.decode(message)
            Log.d("Log", "log(BaseRetrofit.kt:42)------>$decode")
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    private inner class LogInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            val request = chain.request()
            val t1 = System.nanoTime()//请求发起的时间
            Log.d("ShLog---", "(BaseApiRetrofit.kt:293)------>" + String.format("发送请求: %s on %s%n%s%s",
                    request.url(), chain.connection(), request.headers(), request.body().toString()))
            val response = chain.proceed(request)
            val t2 = System.nanoTime()//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            Log.d("ShLog---", "(BaseApiRetrofit.kt:300)------>" + String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    getResponseString(response),
                    (t2 - t1) / 1e6,
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