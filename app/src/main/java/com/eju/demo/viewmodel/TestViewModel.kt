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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.*

class TestViewModel:BaseViewModel<TestRepository>() {



    val userInfo=flowLiveData {
        model.getUserInfo()
    }


}