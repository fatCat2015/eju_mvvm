package com.eju.architecture.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class SimpleActivity(@LayoutRes layoutId:Int) :BaseActivity<SimpleViewModel,ViewDataBinding>(layoutId){
}