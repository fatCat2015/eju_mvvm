package com.eju.demo.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.eju.aop.annotation.AvoidMultiExecutions
import com.eju.aop.annotation.Event
import com.eju.aop.event.EventParam
import com.eju.aop.event.EventUploadProxy
import com.eju.aop.event.IEventUpload
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.observe
import com.eju.architecture.widget.PaletteHelper
import com.eju.demo.R
import com.eju.demo.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_test2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject

class TestActivity : BaseActivity<TestViewModel>(R.layout.activity_test2) {

    override fun setListeners() {
        bt1.setOnClickListener {
        }
    }

    private fun test(){
        supportFragmentManager
    }



    override fun initData(savedInstanceState: Bundle?) {


    }



}