package com.eju.network

import android.app.Application
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object NetworkUtil {

    private lateinit var retrofit:Retrofit

    internal lateinit var application:Application

    private val requestHeaders= mutableMapOf<String,String>()

    private val serviceCacheMap= mutableMapOf<Class<*>,Any>()

    private val exceptionConverter:IExceptionConverter by lazy{
        ExceptionConverter()
    }


    fun addRequestHeader(key:String,value:String){
        requestHeaders[key] = value
    }

    fun addRequestHeader(headers:Map<String,String>){
        requestHeaders.putAll(headers)
    }

    fun init(application:Application,baseUrl:String){
        init(application,ApiConfig(baseUrl))
    }

    fun init(application:Application,config: ApiConfig){
        this.application=application
        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(AddHeadersInterceptor(requestHeaders))
            .addInterceptor(createLoggingInterceptor(config.showLog,config.logTag,config.logRequestTag,config.logResponseTag))  //请求信息log 放在最后addInterceptor
            .build()
        retrofit=Retrofit.Builder()
            .baseUrl(config.baseUrl)
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


    private fun convertException(e:Exception?):Exception{
        return exceptionConverter.convert(e)
    }

    suspend fun <T> result(block: suspend() -> BaseResult<T>):T{
        try {
            return block().result
        } catch (e: Exception) {
            throw convertException(e)
        }
    }



}


class ApiConfig(val baseUrl:String)  {
    var showLog: Boolean = BuildConfig.DEBUG
    var logTag: String = "NetworkUtil"
    var logRequestTag: String? = null
        get() {
            return if(field.isNullOrEmpty()){
                "${logTag}-request"
            }else{
                field
            }
        }
    var logResponseTag: String? = null
        get() {
            return if(field.isNullOrEmpty()){
                "${logTag}-response"
            }else{
                field
            }
        }
}

