/**
 * Copyright 2019 Danny Keng
 */
package com.eju.service

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Add Cookies Interceptor
 */
class AddHeadersInterceptor(private val headers:Map<String,String>?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if(headers.isNullOrEmpty()){
            chain.proceed(chain.request())
        }else{
            val builder = chain.request().newBuilder()
            headers.forEach{
                builder.addHeader(it.key,it.value)
            }
            chain.proceed(builder.build())
        }

    }
}
