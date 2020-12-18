package com.eju.demo.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.eju.architecture.base.BaseActivity
import com.eju.demo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun initViewModels() {
    }

    override fun setListeners() {
        bt0.setOnClickListener {
            startActivity(Intent(this, DetailAct::class.java))
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

    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}