package com.example.observer

import com.example.base.BaseView
import com.example.exception.AppError
import com.example.exception.AppErrorUtil
import com.example.reponse.BaseResponse
import io.reactivex.observers.DisposableObserver

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
abstract class AppObserver<T>(private var view: BaseView?) : DisposableObserver<T>() {
    override fun onComplete() {

    }

    override fun onNext(t: T) {
        when (t) {
            is AppError -> {
                onSuccess(t)
            }
            is BaseResponse -> {
                if (t.status == "1") {
                    onSuccess(t)
                } else {
                    onFail(AppErrorUtil.handler(t))
                }
            }
            else -> onSuccess(t)
        }
    }

    override fun onError(e: Throwable?) {
        val appError: AppError = AppErrorUtil.handler(e)
        onFail(appError)
    }

    abstract fun onSuccess(t: T)

    abstract fun onFail(appError: AppError)
}