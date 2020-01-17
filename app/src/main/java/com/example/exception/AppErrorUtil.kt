package com.example.exception

import com.example.reponse.BaseResponse
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.EOFException
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
class AppErrorUtil {
    companion object {
        fun <T> handler(error: T): AppError {
            return when (error) {
                is BaseResponse -> {
                    dealResponseError(error)
                }
                is Throwable -> {
                    dealExceptionError(error)
                }
                else -> {
                    AppError.UNKNOWN.setMessage(error.toString())
                }
            }
        }

        private fun dealResponseError(response: BaseResponse): AppError {
            return when (response.infocode) {
                else -> AppError.UNKNOWN.setMessage(response.infocode)
            }
        }

        private fun dealExceptionError(exception: Throwable): AppError {
            exception.printStackTrace()
            return when (exception) {
                is EOFException -> AppError.E_LOCAL_JSON_SYNTAX_EXCEPTION
                is SSLHandshakeException -> AppError.E_LOCAL_ETCA_SSL_EXCEPTION
                is IllegalArgumentException -> AppError.E_LOCAL_ILLEGAL_ARGUMENT
                is IllegalStateException -> AppError.E_LOCAL_ILLEGAL_STATE
                is NullPointerException -> AppError.E_LOCAL_NULL_POINTER
                is SocketTimeoutException -> AppError.E_NET_REQ_TIMEOUT
                is JsonSyntaxException -> AppError.E_LOCAL_JSON_SYNTAX_EXCEPTION
                is ConnectException, is UnknownHostException -> {
                    if (exception.message?.contains("Failed to connect to") != false) {
                        AppError.E_NET_CONNECT_SERVER_FAIL
                    } else {
                        AppError.E_NET_CONNECT_FAIL
                    }
                }
                else -> AppError.UNKNOWN.setMessage(exception.message.toString())
            }
        }
    }
}