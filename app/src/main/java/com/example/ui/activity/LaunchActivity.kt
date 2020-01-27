package com.example.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.utils.JumpPermissionSetting
import pub.devrel.easypermissions.EasyPermissions
import java.util.*


class LaunchActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        const val LOCATION = 0x001
    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindow()
        setContentView(R.layout.activity_launch)
        initRequestPermissions()
    }

    private fun setWindow() {
        val attributes = window.attributes
        attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        val decorView = window.decorView
        decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            var decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun initRequestPermissions() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            init()
        } else {
            EasyPermissions.requestPermissions(this, "需要定位权限", LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
        }
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

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            LOCATION -> {
                JumpPermissionSetting.jump(this, LOCATION)
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            LOCATION -> {
                initRequestPermissions()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}
