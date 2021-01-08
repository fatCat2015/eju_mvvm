package com.eju.architecture.base

import com.eju.service.ServiceUtil
import com.eju.service.cache.CacheConfig
import com.eju.service.cache.CacheStrategy
import com.eju.service.cache.ServiceCache
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

open class BaseRepository {

    fun <T> getApi(clazz: Class<T>) = ServiceUtil.getService(clazz)

    suspend fun <T> execute(block:suspend CoroutineScope.()->T):T{
        return withContext(Dispatchers.IO){
            block()
        }
    }

    fun <T> getDataFromCache(key:String, cacheConfig:CacheConfig=CacheConfig(), block:suspend ()->T):Flow<T>{
        return ServiceCache.getData(key=key,cacheConfig = cacheConfig,block = block)
    }

    open fun onDestroy(){

    }
}