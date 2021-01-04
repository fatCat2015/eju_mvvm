package com.eju.architecture

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.eju.architecture.base.BaseApp
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.StringBuilder

val application=BaseApp.application


/**
 * 获取当前进程的名称，默认进程名称是包名
 */
val currentProcessName: String?
    get() {
        val pid = android.os.Process.myPid()
        val mActivityManager = application.getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }

fun isInUIThread() = Looper.getMainLooper().thread == Thread.currentThread()

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

fun launch(
    onError:((Exception)->Boolean)? = null,
    onComplete:(()->Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
):Job{
    return GlobalScope.launch{
        try {
            block()
        } catch (e: Exception) {
            if(onError?.invoke(e)!=true){
                ApiExceptionHandler.handle(e)
            }
        } finally {
            onComplete?.invoke()
        }
    }
}

