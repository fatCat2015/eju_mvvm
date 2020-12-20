package com.eju.demo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.coroutines.CoroutineContext

class MultiViewModel:BaseViewModel() {

    val neededData=MutableLiveData<HelpDetail>()


    fun demo1(){
        execute ({
            val firstData=NetworkUtil.getService(DemoService::class.java).getHelpDetail("58").result
            val secondData=NetworkUtil.getService(DemoService::class.java).getHelpDetail("58").result
            //接口顺序执行 firstData ->sendData
            secondData
        }){
            neededData.postValue(it)
        }
    }

    fun demo2(){
        //todo async中的异常不会抛出 怎么处理
        execute({
            val onePartData0=async() {
                NetworkUtil.getService(DemoService::class.java).getHelpDetail("58").result
            }
            val onePartData1=async() {
                NetworkUtil.getService(DemoService::class.java).getHelpDetail("58").result
            }
            onePartData0.await()
            onePartData1.await()

        }){
            //接口并发执行 运行至此 接口都执行完毕
            neededData.postValue(it)
        }
    }

}