package com.eju.architecture.base

import com.eju.service.BaseResult
import com.eju.service.ServiceUtil
import com.eju.service.ServiceCache
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import java.util.concurrent.TimeUnit

open class BaseRepository {

    protected fun <T> getApi(clazz: Class<T>) = ServiceUtil.getService(clazz)

    protected suspend fun <T> runOnCoroutine(block:suspend CoroutineScope.()->T):T{
        return withContext(Dispatchers.IO){
            block()
        }
    }


    suspend fun <T> useCacheIfExits(cachedTime:Long?=null, cachedTimeUnit: TimeUnit?=null,
                                    block:()-> Call<BaseResult<T>>
    ):T{
        return ServiceCache.useCacheIfExits(cachedTime,cachedTimeUnit,block)
    }


    fun <T> firstCacheThenRemote(cachedTime:Long?=null, cachedTimeUnit: TimeUnit?=null,
                                 block: ()->Call<BaseResult<T>>
    ):Flow<T>{
        return ServiceCache.firstCacheThenRemote(cachedTime,cachedTimeUnit,block)
    }






    open fun onDestroy(){

    }
}