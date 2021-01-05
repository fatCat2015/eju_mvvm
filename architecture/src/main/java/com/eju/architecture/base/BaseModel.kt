package com.eju.architecture.base

import com.eju.service.ServiceUtil
import kotlinx.coroutines.*

open class BaseModel {

    fun <T> getApi(clazz: Class<T>) = ServiceUtil.getService(clazz)

    suspend fun <T> execute(block:suspend CoroutineScope.()->T):T{
        return withContext(Dispatchers.IO){
            block()
        }
    }

    open fun onDestroy(){

    }
}