package com.eju.demo.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.eju.architecture.base.BaseViewModel
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.TestRepository
import com.eju.service.ServiceUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.*

class TestViewModel:BaseViewModel<TestRepository>() {

    val userInfo:LiveData<HelpDetail> =liveData {
        Log.i("sck220", "TestViewModel->getUserInfo: ${isInUIThread()} ")
        model.getUserInfo("58")
    }

    val userInfo1:LiveData<HelpDetail> get(){
        return liveData {
            Log.i("sck220", "TestViewModel->getUserInfo: ${isInUIThread()} ")
            model.getUserInfo("58")
        }
    }





    private var lasJob:Job?=null


    var query=MutableLiveData<String>()


    fun test1():LiveData<String> =query.switchMap {
        lasJob?.cancel()
        val job=Job(viewModelScope.coroutineContext[Job]).also { lasJob=it }
        androidx.lifecycle.liveData<String>(job+Dispatchers.IO) {
            delay(2000)
            Log.i("sck220", "test: 11111111")
            emit(test11())
        }
    }

    suspend fun test11():String{
        Log.i("sck220", "test11: ")
        ServiceUtil.getService(DemoService::class.java).getHelpDetail("58")
        return Random().nextInt(20).toString()
    }



    val followCount = model.getFollowCount()
        .flowOn(Dispatchers.IO)
        .onStart {
            emit(3000)
        }
        .map {
            it+10
        }
        .asLiveData()


}