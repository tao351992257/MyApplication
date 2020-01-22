package com.example.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
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
        setContentView(R.layout.activity_launch)
        initRequestPermissions()
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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this)
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}
