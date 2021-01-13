package com.eju.service

import com.eju.cache.CacheBlock
import com.eju.cache.CacheConfig
import com.eju.cache.CacheProxy
import com.eju.cache.CacheStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.FormBody
import okhttp3.Request
import retrofit2.Call
import java.lang.StringBuilder
import java.net.ConnectException
import java.util.concurrent.TimeUnit


object ServiceCache {

    private val cacheProxy:CacheProxy by lazy {
        CacheProxy(ServiceUtil.application)
    }

    fun <T> getData(cachedKey:String,networkConnected:Boolean=true,cacheConfig: CacheConfig,cacheStrategy: CacheStrategy, block:suspend ()-> BaseResult<T>):Flow<T>{
        return cacheProxy.getData(cachedKey,cacheConfig,cacheStrategy,block = {
            if(networkConnected){
                block().result
            }else{
                throw ConnectException(ServiceUtil.application.getString(R.string.ConnectException))
            }
        })
    }

    fun <T> getData(networkConnected:Boolean=true,cacheConfig: CacheConfig,cacheStrategy: CacheStrategy, block:()-> Call<BaseResult<T>>):Flow<T>{
        val call=block()
        val cachedKey=generateCachedKey(call.request())
        return cacheProxy.getData(cachedKey,cacheConfig,cacheStrategy,block = {
            if(networkConnected){
                call.awaitResult()
            }else{
                throw ConnectException(ServiceUtil.application.getString(R.string.ConnectException))
            }
        })
    }


    private fun generateCachedKey(request: Request):String{
        val url=request.url.toString()
        val method=request.method
        val key="${method} ${url}"
        val body=request.body
        return if(body is FormBody) {
            val size=body.size
            val params= StringBuilder()
            for (index in 0 until size){
                params.append(" ${body.encodedName(index)}=${body.encodedValue(index)}")
            }
            "${key}${params}"
        } else key
    }


    fun remove(key:String){
        cacheProxy.remove(key)
    }

    fun clear(){
        cacheProxy.clear()
    }



}