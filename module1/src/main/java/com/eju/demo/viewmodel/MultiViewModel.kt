package com.eju.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.MultiRepository

class MultiViewModel:BaseViewModel<MultiRepository>() {

    val neededData=liveData {
        model.demo1()
    }
    val neededData1=liveData {
        model.demo2()
    }


}