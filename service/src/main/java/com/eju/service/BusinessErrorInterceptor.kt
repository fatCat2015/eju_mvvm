package com.eju.service

import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8

/**
 * 业务错误 Interceptor
 * 对于request: 无
 * 对于response:负责解析业务错误（在http status 成功的前提下）
 */
class BusinessErrorInterceptor(private val businessResultVerifier: BusinessResultVerifier) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var response = chain.proceed(chain.request())
        //http status不是成功的情况下，我们不处理
        if (!response.isSuccessful){
            return response
        }

        //因为response.body().string() 只能调用一次，所以这里读取responseBody不使用response.body().string()，原因：https://juejin.im/post/6844903545628524551
        //以下读取resultString的代码节选自
        //https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/main/kotlin/okhttp3/logging/HttpLoggingInterceptor.kt

        val responseBody = response.body!!
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source.buffer
        val contentType = responseBody.contentType()
        val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8
        val resultString = buffer.clone().readString(charset)


        val jsonObject = JSONObject(resultString)
        if (!businessResultVerifier.hasErrorCode(jsonObject)) {
            return response
        }
        val errorCode=businessResultVerifier.getErrorCode(jsonObject)
        if(businessResultVerifier.businessIsSuccess(errorCode)){
            return response   //对于业务成功的情况不做处理
        }
        val errorMsg=businessResultVerifier.getErrorMsg(jsonObject)  //业务失败的情况直接抛出ApiException
        throw ApiException(errorCode, errorMsg)
    }

}


