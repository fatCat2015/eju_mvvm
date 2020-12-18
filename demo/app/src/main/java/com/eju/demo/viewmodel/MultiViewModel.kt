package com.eju.demo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import java.lang.Exception

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
        execute({
            try {
                val onePartData0=async {
                    NetworkUtil.getService(DemoService::class.java).getHelpDetail("58").result
                }
            }catch (e:Exception){
                Log.i("sck220", "Exception111: ")
            }

        }){
            //接口并发执行 运行至此 接口都执行完毕
//            neededData.postValue(it)
        }
    }

}