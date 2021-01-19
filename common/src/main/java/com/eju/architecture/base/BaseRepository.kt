package com.eju.architecture.base

import com.eju.architecture.util.NetworkManager
import com.eju.cache.CacheConfig
import com.eju.cache.CacheStrategy
import com.eju.service.BaseResult
import com.eju.service.ServiceUtil
import com.eju.service.ServiceCache
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Call

open class BaseRepository {

    protected fun <T> getApi(clazz: Class<T>) = ServiceUtil.getService(clazz)

    protected suspend fun <T> runOnCoroutine(block:suspend CoroutineScope.()->T):T{
        return withContext(Dispatchers.IO){
            block()
        }
    }

    protected fun <T> firstCacheThenRemote(cacheConfig: CacheConfig= CacheConfig.DEFAULT, block:()-> Call<BaseResult<T>>):Flow<T>{
        return ServiceCache.getData(NetworkManager.networkConnected(), cacheConfig,
            CacheStrategy.FIRST_CACHE_THEN_REMOTE,block)
    }

    /**
     * @param cacheStrategy 除了CacheStrategy.FIRST_CACHE_THEN_REMOTE (使用firstCacheThenRemote())
     */
    protected suspend fun <T> fromCache(cacheConfig: CacheConfig, cacheStrategy: CacheStrategy, block:()-> Call<BaseResult<T>>):T{
        return ServiceCache.getData(NetworkManager.networkConnected(), cacheConfig,cacheStrategy,block).first()
    }

    protected suspend fun <T> fromCache(block:()-> Call<BaseResult<T>>):T{
        return fromCache(
            CacheConfig.DEFAULT,
            CacheStrategy.USE_CACHE_IF_REMOTE_FAILED,
            block)
    }



    protected fun <T> firstCacheThenRemote(key:String,cacheConfig: CacheConfig= CacheConfig.DEFAULT, block:suspend ()-> BaseResult<T>):Flow<T>{
        return ServiceCache.getData(key,NetworkManager.networkConnected(), cacheConfig,
            CacheStrategy.FIRST_CACHE_THEN_REMOTE,block)
    }

    /**
     * @param cacheStrategy 除了CacheStrategy.FIRST_CACHE_THEN_REMOTE (使用firstCacheThenRemote())
     */
    protected suspend fun <T> fromCache(key:String,cacheConfig: CacheConfig, cacheStrategy: CacheStrategy, block:suspend ()-> BaseResult<T>):T{
        return ServiceCache.getData(key,NetworkManager.networkConnected(), cacheConfig,cacheStrategy,block).first()
    }

    protected suspend fun <T> fromCache(key:String,block:suspend ()-> BaseResult<T>):T{
        return fromCache(key,
            CacheConfig.DEFAULT,
            CacheStrategy.USE_CACHE_IF_REMOTE_FAILED,
            block)
    }

    open fun onDestroy(){

    }
}