package com.eju.demo

import android.app.Application
import com.eju.architecture.base.BaseApp
import com.eju.network.NetworkUtil

class App:BaseApp() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtil.init("http://testapi.ichongyou.cn/")

    }
}