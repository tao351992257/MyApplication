package com.example.presenter

import android.content.Context
import com.amap.api.location.AMapLocation
import com.example.base.BaseView
import com.example.contract.WeatherContract
import com.example.exception.AppError
import com.example.exception.AppErrorUtil
import com.example.exception.AppException
import com.example.globle.NetConst
import com.example.model.WeatherModel
import com.example.observer.AppObserver
import com.example.observer.RxJavaExecute
import com.example.reponse.BaseResponse
import com.example.reponse.WeatherResponse
import com.example.utils.QMUIDialog
import io.reactivex.Observable
import java.lang.NullPointerException

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
class WeatherPresenter : WeatherContract.Presenter, RxJavaExecute() {

    private var weatherModel: WeatherContract.Model? = null
    private var view: WeatherContract.View? = null

    override fun onCreate(view: BaseView) {
        this.view = view as WeatherContract.View
        weatherModel = WeatherModel()
    }

    override fun getWeather(location: AMapLocation, context: Context) {
        view?.showLoading(context)
        val observable = weatherModel?.getWeather(location.adCode, context) ?: return
        observable.concatMap { emit ->
            Observable.create<WeatherResponse> {
                if (emit.status == NetConst.NET_ERROR) {
                    it.onError(AppException(emit))
                } else {
                    it.onNext(emit)
                }
            }
        }
        val appObservable = object : AppObserver<WeatherResponse>(view) {
            override fun onSuccess(response: WeatherResponse) {
                view?.hideLoading()
                view?.showWeather(location, response)
            }

            override fun onFail(appError: AppError) {
                view?.hideLoading()
                view?.showError(appError)
            }

        }
        execute(appObservable, observable)
    }

    override fun onDestory() {
        this.view = null
    }
}