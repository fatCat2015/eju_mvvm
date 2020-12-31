package com.eju.architecture.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<VM:BaseViewModel<*>,B:ViewDataBinding>(private val layoutId:Int): BaseActivity<VM>(0) {

    protected lateinit var binding:B

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this,layoutId)
        binding.lifecycleOwner=this
        super.onCreate(savedInstanceState)
    }
}