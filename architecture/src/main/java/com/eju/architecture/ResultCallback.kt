package com.eju.architecture

interface ResultCallback<T> {
    fun onSuccess(data:T)

    fun onFailed(e:Throwable):Boolean{
        return false
    }

    fun onComplete(){

    }
}