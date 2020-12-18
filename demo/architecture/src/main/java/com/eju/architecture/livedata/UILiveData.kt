package com.eju.architecture.livedata

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.isInUIThread

open class UILiveData<T>:MutableLiveData<T>() {

    fun changeValue(newValue:T?){
        if(isInUIThread()){
            value=newValue
        }else{
            postValue(newValue)
        }
    }

}