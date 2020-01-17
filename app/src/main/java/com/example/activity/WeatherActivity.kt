package com.example.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.base.BaseApplication
import com.example.contract.WeatherContract
import com.example.exception.AppError
import com.example.globle.AppConst
import com.example.net.ApiRetrofit
import com.example.presenter.WeatherPresenter
import com.example.reponse.BaseResponse
import com.example.reponse.WeatherResponse
import com.example.utils.KeyboardUtil
import com.example.utils.SystemUtils
import com.example.utils.TextUtils.Companion.stringToJson
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherActivity : AppCompatActivity(), WeatherContract.View {
    private var weatherPresenter: WeatherPresenter? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherPresenter = WeatherPresenter()
        weatherPresenter?.onCreate(this)
        init()
    }

    private fun init() {
        btnRequest.setOnClickListener {
            Log.d("Log", "init(WeatherActivity.kt:37)------>")
            weatherPresenter?.getWeather(etAdcode.text.trim().toString(), this)
//            val request = ApiRetrofit.getInstance(this).Request("110100")
//            request.enqueue(object : Callback<BaseResponse> {
//                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//                    Log.d("Log", "onFailure(WeatherActivity.kt:47)------>")
//                }
//
//                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
//                    val body = response.body()
//                    val info = body?.infocode
//                    Log.d("TAG", "onResponse: $info")
//                }
//            })

//            Thread(Runnable { loadData(etAdcode.text.trim().toString()) }).start()
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
                    Log.d("Log", "loadData(WeatherActivity.kt:37)------>$it")
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
}
