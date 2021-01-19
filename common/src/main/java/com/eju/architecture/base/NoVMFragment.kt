package com.eju.architecture.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class NoVMFragment<B:ViewDataBinding>(@LayoutRes layoutId:Int) :BaseFragment<SimpleViewModel,B>(layoutId){
}