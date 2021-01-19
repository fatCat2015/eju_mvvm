package com.eju.demo.viewmodel

import com.eju.architecture.base.BaseViewModel
import com.eju.demo.model.OneShotRepository

class OneShotViewModel(private val id:String):BaseViewModel<OneShotRepository>() {

    val detail=liveData {
        model.queryDetail(id)
    }
}