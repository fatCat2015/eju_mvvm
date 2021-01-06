package com.eju.architecture

import android.content.res.Resources
import android.util.TypedValue
import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.StringBuilder


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




val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this, Resources.getSystem().displayMetrics)

val Int.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this.toFloat(), Resources.getSystem().displayMetrics)

fun String?.list(separator:String=","):List<String>{
    return this?.split(separator)?.filter { it.isNotEmpty() } ?: emptyList()
}

fun <T> Collection<T>?.fold(separator:String=",",converter:((T)->String)?=null):String{
    val stringBuilder= StringBuilder()
    this?.forEach {item->
        stringBuilder.append(converter?.let { converter.invoke(item) }?:item)
        stringBuilder.append(separator)
    }
    if(stringBuilder.isNotEmpty()){
        stringBuilder.deleteCharAt(stringBuilder.length-1)
    }
    return stringBuilder.toString()
}






