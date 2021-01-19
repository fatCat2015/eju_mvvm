package com.eju.demo.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.eju.architecture.base.SimpleActivity
import com.eju.architecture.router.RouterPath
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
            ARouter.getInstance().build(RouterPath.Module1.list).navigation()
//            startActivity(Intent(this, ListActivity::class.java))
        }
        bt4.setOnClickListener {
            IMContext.loginIm()
        }
        btTest.setOnClickListener {
//            startActivity(Intent(this, TestActivity::class.java))
            val uri= Uri.parse("eju://mobile.app.yilou${RouterPath.Module2.main}?name=fkjsfkesjfkesfkjes")
            ARouter.getInstance().build(uri).navigation()
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