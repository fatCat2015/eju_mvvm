package com.eju.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.model.TestModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TestViewModel:BaseViewModel<TestModel>() {


    fun  aa():LiveData<String> =model.aa()


    fun a(){
        viewModelScope.launch {
            CancellationException()
        }
    }
}