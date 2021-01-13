package com.eju.service

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object ServiceUtil {

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
        val okHttpClient=
            RetrofitUrlManager.getInstance()
                .with(
                    OkHttpClient.Builder()
                        .apply {
                            if(config.showLog){
                                addNetworkInterceptor(StethoInterceptor())
                            }
                        }
                        .addInterceptor(AddHeadersInterceptor(requestHeaders))
                )
                .addInterceptor(createLoggingInterceptor(config.showLog,config.logTag,config.logRequestTag,config.logResponseTag))  //请求信息log 放在最后addInterceptor
                .build()

        if(config.showLog){
            Stetho.initializeWithDefaults(application)
        }

        retrofit=Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        UrlManager.putUrls()
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


    fun convertNetException(e:Throwable):Throwable{
        return exceptionConverter.convert(e)
    }




}


object UrlManager{

    private const val HEADER_VALUE_YUN = "yun"

    const val DOMAIN_YUN = "Domain-Name:${HEADER_VALUE_YUN}"

    internal fun putUrls() {
        RetrofitUrlManager.getInstance().putDomain(HEADER_VALUE_YUN, "http://www.baidu.com")
    }

}


class ApiConfig(val baseUrl:String)  {
    var showLog: Boolean = BuildConfig.DEBUG
    var logTag: String = "ServiceUtil"
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

suspend fun <T> Call<BaseResult<T>>.awaitResult():T{
    return try {
        await().result
    } catch (e: Exception) {
        throw ServiceUtil.convertNetException(e)
    }
}
