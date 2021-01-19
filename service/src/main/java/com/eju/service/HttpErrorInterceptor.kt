package com.eju.service

import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8

/**
 * 业务错误 Interceptor
 * 对于request: 无
 * 对于response:负责解析业务错误（在http status 成功的前提下）
 */
class HttpErrorInterceptor(private val httpExceptionConverter: HttpExceptionConverter) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: Exception) {
            throw httpExceptionConverter.convert(e)
        }
    }

}


