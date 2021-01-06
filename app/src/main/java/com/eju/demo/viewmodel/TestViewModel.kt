package com.eju.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.model.TestModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TestViewModel:BaseViewModel<TestModel>() {

    val testLiveData=MutableLiveData<String>()


    fun aa()= model.test()


}