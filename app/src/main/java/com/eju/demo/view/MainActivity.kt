package com.eju.demo.view

import android.content.Intent
import android.os.Bundle
import com.eju.architecture.base.NoVMActivity
import com.eju.architecture.base.SimpleActivity
import com.eju.demo.IMContext
import com.eju.demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SimpleActivity(R.layout.activity_main) {



    override fun setListeners() {
        bt0.setOnClickListener {
            startActivity(Intent(this, OneShotActivity::class.java))
        }
        bt1.setOnClickListener {
            startActivity(Intent(this, OrderDetailAct::class.java))
        }
        bt2.setOnClickListener {
            startActivity(Intent(this, MultiApiAct::class.java))
        }
        bt3.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
        bt4.setOnClickListener {
            IMContext.loginIm()
        }
        btTest.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
        bt5.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        bt6.setOnClickListener {
            startActivity(Intent(this, CacheActivity::class.java))
        }
        bt7.setOnClickListener {
            startActivity(Intent(this, FragmentDemoActivity::class.java))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }


    override fun onDestroy() {
        super.onDestroy()
        IMContext.cancel()
    }

}