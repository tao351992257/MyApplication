package com.example.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.globle.AppConst
import com.example.utils.KeyboardUtil
import com.example.utils.SystemUtils
import com.example.utils.TextUtils.Companion.stringToJson
import kotlinx.android.synthetic.main.activity_weather.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        btnRequest.setOnClickListener {
            val ignoringBatteryOptimizations = SystemUtils.isIgnoringBatteryOptimizations(this)
            if (!ignoringBatteryOptimizations) {
                SystemUtils.requestIgnoreBatteryOptimizations(this)
            }
            KeyboardUtil.hideKeyboard(etAdcode)
            Thread(Runnable { loadData(etAdcode.text.trim().toString()) }).start()
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
}
