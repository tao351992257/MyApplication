package com.example.base

import android.app.Application
import android.content.Context
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/5
 */
class BaseApplication : Application() {
    companion object {
        private lateinit var applicationContext: Context
        fun getApplicationContext(): Context {
            return applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.applicationContext = applicationContext
        QMUISwipeBackActivityManager.init(this)
    }
}