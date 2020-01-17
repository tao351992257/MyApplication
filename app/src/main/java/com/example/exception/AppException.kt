package com.example.exception

import com.example.reponse.BaseResponse
import kotlin.Exception

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
class AppException : Exception {
    constructor(t: Throwable) : super(t)
    constructor(baseResponse: BaseResponse) : super(AppErrorUtil.handler(baseResponse).getValue().toString(), Exception(baseResponse?.infocode))
}