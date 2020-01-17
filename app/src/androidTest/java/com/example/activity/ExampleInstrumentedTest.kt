package com.example.activity

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.net.ApiRetrofit.Companion.getInstance
import com.example.reponse.BaseResponse
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/17
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() { // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.myapplication", appContext.packageName)
        val request = getInstance(appContext).Request("")
        request.enqueue(object : Callback<BaseResponse?> {
            override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
                val body = response.body()
                val info = body!!.infocode
                Log.d("TAG", "onResponse: $info")
            }

            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                Log.d("TAG", "onFailure: ")
            }
        })
    }
}