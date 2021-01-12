package com.eju.service

import com.eju.cache.CacheBlock
import com.eju.cache.CacheProxy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.FormBody
import okhttp3.Request
import retrofit2.Call
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit


object ServiceCache {

    private val cacheProxy:CacheProxy by lazy {
        CacheProxy(ServiceUtil.application)
    }

    suspend fun <T> useCacheIfExits(cachedTime:Long?=null, cachedTimeUnit: TimeUnit?=null,
                                    block:()-> Call<BaseResult<T>>
    ):T{
        val call=block()
        val cachedKey=generateCachedKey(call.request())
        return cacheProxy.useCacheIfExits(cachedKey,cachedTime?:CacheBlock.CACHED_FOREVER,cachedTimeUnit?:TimeUnit.MILLISECONDS,block={
            call.awaitResult()
        })
    }

    fun <T> firstCacheThenRemote(cachedTime:Long?=null, cachedTimeUnit: TimeUnit?=null,
                                 block:()->Call<BaseResult<T>>
    ):Flow<T>{
        val call=block()
        val cachedKey=generateCachedKey(call.request())
        return cacheProxy.firstCacheThenRemote(cachedKey,cachedTime?:CacheBlock.CACHED_FOREVER,cachedTimeUnit?:TimeUnit.MILLISECONDS) {
            call.awaitResult()
        }
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