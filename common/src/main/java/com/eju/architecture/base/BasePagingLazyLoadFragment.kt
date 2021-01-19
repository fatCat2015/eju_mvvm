package com.eju.architecture.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.eju.architecture.observe

abstract class BasePagingLazyLoadFragment<VM:BasePagingViewModel<*,*>,B:ViewDataBinding>(@LayoutRes layoutId:Int):BasePagingFragment<VM,B>(layoutId) {

    private var savedInstanceState: Bundle?=null

    private var isLoaded=false


    override fun setListeners() {

    }


    override fun initData(savedInstanceState: Bundle?) {
        this.savedInstanceState=savedInstanceState
    }


    override fun onResume() {
        super.onResume()
        if(loadWheneverVisible()){
            lazyLoad(savedInstanceState)
            isLoaded=true
        }else if(!isLoaded){
            lazyLoad(savedInstanceState)
            isLoaded=true
        }
    }

    /**
     * 返回true表示可见时即加载 false表示加载一次
     */
    protected open fun loadWheneverVisible()=false


    abstract fun lazyLoad(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded=false
    }

}