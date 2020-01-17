package com.example.exception

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/16
 */
enum class AppError constructor(private val code: Int, private var msg: String?) {
    UNKNOWN(0xFFFFFF, "未知错误"),
    E_LOCAL_JSON_SYNTAX_EXCEPTION(0x00001B, "数据解析异常"),
    E_LOCAL_ETCA_SSL_EXCEPTION(0x00001D, "SSL证书异常，连接服务器失败"),
    E_LOCAL_ILLEGAL_ARGUMENT(0x000008, "参数不合法"),
    E_LOCAL_ILLEGAL_STATE(0x000009, "无效状态异常"),
    E_LOCAL_NULL_POINTER(0x000011, "返回为空"),
    E_NET_REQ_TIMEOUT(0x10000C, "网络请求超时"),
    E_NET_CONNECT_SERVER_FAIL(0x000122, "无法连接到服务器"),
    E_NET_CONNECT_FAIL(0x000123, "网络连接失败");
    fun getValue(): Int {
        return this.code
    }

    fun getMessage(): String? {
        return this.msg
    }

    fun setMessage(msg: String?): AppError {
        this.msg = msg
        return this
    }

    companion object {
        fun valueOf(code: Int?): AppError = values().find { it.getValue() == code } ?: UNKNOWN
    }

    override fun toString(): String {
        return "AppError(code=$code,msg=$msg)"
    }
}