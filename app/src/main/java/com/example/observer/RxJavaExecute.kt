package com.example.observer

import android.annotation.SuppressLint
import androidx.core.util.Preconditions
import com.example.exception.AppError
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
abstract class RxJavaExecute {
    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    @SuppressLint("RestrictedApi")
    fun <T> execute(observer: DisposableObserver<T>, observableT: Observable<T>) {
        Preconditions.checkNotNull(observer)
        val observable = observableT.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribeWith(observer))
    }

    fun <T> execute(observableT: Observable<T>) {
        val appObserver = object : AppObserver<T>(null) {
            override fun onSuccess(t: T) {

            }

            override fun onFail(appError: AppError) {

            }
        }
        Preconditions.checkNotNull(appObserver)
        val observable = observableT
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribeWith(appObserver))
    }

    fun <T> executeNoDispose(observer: Observer<T>, observable: Observable<T>) {
        Preconditions.checkNotNull(observer)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun <T> executeSync(observer: Observer<T>, observable: Observable<T>) {
        Preconditions.checkNotNull(observer)
        observable.subscribe(observer)
    }


    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }


    fun clear() {
        disposables.clear()
    }


    fun hasDisposables(): Boolean {
        return disposables.size() > 0
    }

    private fun <T> addDisposable(disposable: DisposableObserver<T>?) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(disposables)
        disposables.add(disposable)
    }
}