package com.example.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*


class LaunchActivity : AppCompatActivity() {

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        init()
    }

    private fun init() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@LaunchActivity, WeatherActivity::class.java))
                finish()
            }
        }
        timer?.schedule(timerTask, 3000)
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}
