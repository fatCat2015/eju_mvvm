package com.eju.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtil {

    private lateinit var retrofit:Retrofit

    private val requestHeaders= mutableMapOf<String,String>()

    private val serviceCacheMap= mutableMapOf<Class<*>,Any>()

    fun addRequestHeader(key:String,value:String){
        requestHeaders[key] = value
    }

    fun addRequestHeader(headers:Map<String,String>){
        requestHeaders.putAll(headers)
    }

    fun init(baseUrl:String,showLog:Boolean=BuildConfig.DEBUG,logTag:String="NetworkUtil",requestLogTag:String?=null,responseLogTag:String?=null){
        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(AddHeadersInterceptor(requestHeaders))
            .addInterceptor(createLoggingInterceptor(showLog,logTag,requestLogTag,responseLogTag))  //请求信息log 放在最后addInterceptor
            .build()
        retrofit=Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    private fun createLoggingInterceptor(showLog:Boolean,logTag:String,requestLogTag:String?,responseLogTag:String?): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(if(showLog) Level.BASIC else Level.NONE)
            .tag(logTag)
            .request(requestLogTag)
            .response(responseLogTag)
            .build()
    }

    fun <T> getService(clz:Class<T>):T{
        return serviceCacheMap[clz]?.let {
            it as T
        }?: retrofit.create(clz).also {
            serviceCacheMap.put(clz,it as Any)
        }
    }

    fun <T> getData(response: AppResponse<T>):T{
        return if(response.isSuccess()){
            response.data?.let {
                it
            }?: "" as T
        }else{
            throw ApiException(response.code,response.message)
        }
    }


}


