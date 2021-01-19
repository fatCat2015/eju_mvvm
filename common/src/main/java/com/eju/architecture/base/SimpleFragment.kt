package com.eju.architecture.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class SimpleFragment(@LayoutRes layoutId:Int) :BaseFragment<SimpleViewModel,ViewDataBinding>(layoutId){
}