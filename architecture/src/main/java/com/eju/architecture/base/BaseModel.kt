package com.eju.architecture.base

import android.util.Log
import androidx.annotation.CallSuper
import com.eju.network.BaseResult
import com.eju.network.NetworkUtil
import kotlinx.coroutines.*

open class BaseModel {

    suspend fun <T> callApi(block:suspend()->BaseResult<T>):T{
        return withContext(Dispatchers.IO){
            NetworkUtil.result(block)
        }
    }

    suspend fun <T> execute(block:suspend CoroutineScope.()->T):T{
        return withContext(Dispatchers.IO){
            block()
        }
    }

    open fun onDestroy(){

    }
}