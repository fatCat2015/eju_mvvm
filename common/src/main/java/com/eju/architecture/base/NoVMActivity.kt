package com.eju.architecture.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class NoVMActivity<B:ViewDataBinding>(@LayoutRes layoutId:Int) :BaseActivity<SimpleViewModel,B>(layoutId){
}