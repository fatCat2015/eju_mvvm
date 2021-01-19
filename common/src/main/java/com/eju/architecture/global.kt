package com.eju.architecture

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import com.eju.architecture.base.BaseApp
import kotlinx.coroutines.*

//全局的一些属性
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


val versionName:String?
    get(){
        return try {
            application.packageManager.getPackageInfo(application.packageName,0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

val versionCode:Int?
    get(){
        return try {
            application.packageManager.getPackageInfo(application.packageName,0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

val screenWidth = application.resources.displayMetrics.widthPixels

val screenHeight = application.resources.displayMetrics.heightPixels


//全局的一些方法

fun isInMainProcess()= currentProcessName== application.packageName

fun launch(
    onError:((Exception)->Boolean)? = null,
    onComplete:(()->Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
):Job{
    return GlobalScope.launch(Dispatchers.Main.immediate){
        try {
            block()
        } catch (e: Exception) {
            if(onError?.invoke(e)!=true){
                ExceptionHandler.handle(e)
            }
        } finally {
            onComplete?.invoke()
        }
    }
}


fun isInUIThread() = Looper.getMainLooper().thread == Thread.currentThread()



