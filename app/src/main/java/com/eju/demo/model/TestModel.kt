package com.eju.demo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.eju.architecture.base.BaseModel
import kotlinx.coroutines.delay

class TestModel:BaseModel() {


    fun aa():LiveData<String>{
        return liveData {
            emit("111")
            delay(3000)
            emit("222")
        }
    }
}