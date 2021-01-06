package com.eju.demo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.SimpleActivity
import com.eju.architecture.util.NetworkManager
import com.eju.demo.IMContext
import com.eju.demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SimpleActivity(R.layout.activity_main) {



    override fun setListeners() {
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
        bt5.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        Log.i("NetworkManager", "initData: ${NetworkManager.networkState}")
    }

    override fun onDestroy() {
        super.onDestroy()
        IMContext.cancel()
    }
}