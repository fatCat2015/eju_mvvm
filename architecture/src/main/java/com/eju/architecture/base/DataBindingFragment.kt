package com.eju.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class DataBindingFragment<B:ViewDataBinding>(@LayoutRes private val layoutId:Int): Fragment() {

    protected lateinit var binding:B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if(layoutId!=0){
            binding=DataBindingUtil.inflate(inflater,layoutId,container,false)
            binding.lifecycleOwner=this
            binding.root
        }else{
            super.onCreateView(inflater, container, savedInstanceState)
        }

    }

}