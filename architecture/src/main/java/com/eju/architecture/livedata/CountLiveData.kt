package com.eju.architecture.livedata

import androidx.lifecycle.MutableLiveData

class CountLiveData():UILiveData<Int>() {


    fun trigger(){
        val value=this.value?:0
        changeValue(value+1)
    }

}