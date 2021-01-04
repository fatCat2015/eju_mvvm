package com.eju.demo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.architecture.base.SimpleViewModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.MultiModel
import com.eju.network.NetworkUtil
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.coroutines.CoroutineContext

class MultiViewModel:BaseViewModel<MultiModel>() {

    val neededData=MutableLiveData<HelpDetail>()
    val neededData1=MutableLiveData<String>()


    fun demo1(){
        launch {
            val data=model.demo1()
            neededData.postValue(data)
        }
    }

    fun demo2(){
        launch {
            val data=model.demo2()
            neededData1.postValue(data)
        }
    }

}