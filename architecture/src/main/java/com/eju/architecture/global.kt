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

/**
 * 启动一个协程 在协程中处理返回数据
 */
fun <T> launch(block: suspend CoroutineScope.() -> T,
               resultCallback: ResultCallback<T>
):Job {
    return GlobalScope.launch (context = Dispatchers.Main){
        try {
            val data=block()
            resultCallback.onSuccess(data)
        } catch (e: Exception) {
            if(!resultCallback.onFailed(e)){
                ApiExceptionHandler.handle(e)
            }
        } finally {
            resultCallback.onComplete()
        }
    }
}

