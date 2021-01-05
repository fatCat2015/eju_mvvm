package com.eju.demo.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.cache.BitmapCache
import com.eju.demo.R
import com.eju.demo.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_test2.*

class TestActivity : BaseActivity<TestViewModel>(R.layout.activity_test2) {

    override fun setListeners() {
        bt1.setOnClickListener {
            val bitmap=BitmapFactory.decodeResource(resources,R.drawable.demo)
            cache.put("${index++}",bitmap)
            Log.i("sck220", "put ${cache.size()}  ${cache.maxSize()}")
        }
    }

    private val cache=BitmapCache<String>(60*1024*1024)

    private var index=1
    override fun initData(savedInstanceState: Bundle?) {

    }

}