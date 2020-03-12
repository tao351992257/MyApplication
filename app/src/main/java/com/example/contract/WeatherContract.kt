package com.example.contract

import android.content.Context
import com.amap.api.location.AMapLocation
import com.example.base.BasePresenter
import com.example.base.BaseView
import com.example.exception.AppError
import com.example.reponse.BaseResponse
import com.example.reponse.WeatherResponse
import com.example.utils.QMUIDialog
import io.reactivex.Observable

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
interface WeatherContract {
    interface Model {
        fun getWeather(adCode: String, context: Context): Observable<WeatherResponse>
    }

    interface View : BaseView {
        override fun showLoading(context: Context) {
            QMUIDialog.showQMUITipDialogLoding(context)
            QMUIDialog.show()
        }

        override fun hideLoading() {
            QMUIDialog.QMUITipDialogDismiss()
        }

        fun showWeather(location: AMapLocation, response: WeatherResponse)

        fun showError(appError: AppError)
    }

    interface Presenter : BasePresenter {
        fun getWeather(location: AMapLocation, context: Context)
    }
}