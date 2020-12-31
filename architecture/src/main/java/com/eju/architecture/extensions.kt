package com.eju.architecture

import androidx.lifecycle.*
import com.eju.network.BaseResult
import com.eju.network.ExceptionConverter
import com.eju.network.NetworkUtil
import kotlinx.coroutines.*
import java.lang.Exception


fun <T> LifecycleOwner.observe(liveData: LiveData<T>?,onChangedCallback:(T)->Unit){
    liveData?.observe(this, Observer<T> { t -> onChangedCallback.invoke(t) })
}

fun <T:ViewModel> ViewModelStoreOwner.getViewModel(modeClass:Class<T>, viewModelCreator:(()->T)?=null):T{
    return getOrCreateViewModel(this,modeClass,viewModelCreator)
}

private fun  <T: ViewModel> getOrCreateViewModel(viewModelStoreOwner: ViewModelStoreOwner, modeClass:Class<T>, viewModelCreator:(()->T)?=null):T{
    return viewModelCreator?.let {
        ViewModelProvider(viewModelStoreOwner,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModelCreator.invoke() as T
            }
        }).get(modeClass)
    }?: ViewModelProvider(viewModelStoreOwner).get(modeClass)
}

fun <T> CoroutineScope.asyncSafely(block: suspend CoroutineScope.() -> T): Deferred<T?> {
    return async {
        try {
            block()
        }catch (e: Exception){
            null
        }
    }
}





