package com.eju.architecture.livedata

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job

class CancelableLiveData<T>(private val job: Job):MutableLiveData<T>() {
    fun cancel(){
        job.cancel()
    }
}