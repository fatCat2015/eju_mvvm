package com.eju.architecture.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DataBindingActivity<B:ViewDataBinding>(@LayoutRes private val layoutId:Int): AppCompatActivity() {

    protected lateinit var binding:B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(layoutId!=0){
            binding=DataBindingUtil.setContentView(this,layoutId)
            binding.lifecycleOwner=this
        }

    }
}