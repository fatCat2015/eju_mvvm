package com.eju.demo.view

import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.base.BaseBindingActivity
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityOneShotBinding
import com.eju.demo.viewmodel.OneShotViewModel

class OneShotActivity:BaseBindingActivity<OneShotViewModel,ActivityOneShotBinding>(R.layout.activity_one_shot) {

    override val viewModelCreator: (() -> OneShotViewModel)?
        get() = { OneShotViewModel("58") }

    override fun setListeners() {
    }

    override fun initData(savedInstanceState: Bundle?) {
        binding.vm=viewModel  //dataBinding
        observe(viewModel.detail){
            Log.i("sck220", "detail: ${it}")
        }
    }
}