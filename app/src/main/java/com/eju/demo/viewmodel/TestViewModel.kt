package com.eju.demo.viewmodel

import androidx.lifecycle.LiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.model.TestModel

class TestViewModel:BaseViewModel<TestModel>() {


    fun  aa():LiveData<String> =model.aa()
}