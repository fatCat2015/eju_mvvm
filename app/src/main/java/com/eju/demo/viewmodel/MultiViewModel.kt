package com.eju.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.MultiModel

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