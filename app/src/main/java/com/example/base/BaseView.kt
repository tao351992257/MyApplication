package com.example.base

import android.content.Context

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
interface BaseView {
    fun showLoading(context: Context)

    fun hideLoading()
}