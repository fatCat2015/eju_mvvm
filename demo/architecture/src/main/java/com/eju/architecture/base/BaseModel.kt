package com.eju.architecture.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

open class BaseModel {

    private val job= Job()

    /**
     * 1. 只有LiveData执行observe的时候 block才会执行
     * 2. block执行完毕后 再次observe 不会执行block 但会执行Observer的onChanged()
     * 3. observe的LifecycleOwner处于inactive后持续timeoutInMs时间后 block会被cancel
     * 4. cancel后 再次observe 不会执行block 也不会执行Observer的onChanged() 此时的LiveData的value为null (和文档注释不一致)
     */
    fun <T> liveData(block: suspend LiveDataScope<T>.() -> Unit): LiveData<T> {
        return androidx.lifecycle.liveData  (context = job, timeoutInMs = 10000, block = block)
    }


    @CallSuper
    open fun onDestroy(){
        job?.cancel()
    }
}