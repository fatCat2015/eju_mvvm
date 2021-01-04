package com.eju.architecture.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.eju.architecture.AppStateTracker
import com.eju.architecture.BuildConfig
import com.eju.architecture.currentProcessName
import com.eju.network.NetworkUtil
import com.imyyq.mvvm.app.AppActivityManager
import timber.log.Timber

open class BaseApp:Application() {

    companion object{
        lateinit var application:Application

    }

    override fun onCreate() {
        super.onCreate()
        application=this

        val processName = currentProcessName
        if (processName == packageName) {
            // 主进程初始化
            registerActivityLifecycleCallbacks()   //ActivityLifecycleCallbacks
            AppStateTracker.init()   //ProcessLifecycleOwner
            NetworkUtil.init(this,BuildConfig.baseUrl)  //Retrofit
            if(BuildConfig.DEBUG){     //log
                Timber.plant(Timber.DebugTree())
            }
            onMainProcessInit()
        } else {
            // 其他进程初始化
            processName?.let { onOtherProcessInit(it) }
        }
    }


    private fun registerActivityLifecycleCallbacks(){
        registerActivityLifecycleCallbacks(object:ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
            }
            override fun onActivityStarted(activity: Activity) {
            }
            override fun onActivityDestroyed(activity: Activity) {
                AppActivityManager.remove(activity)
            }
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }
            override fun onActivityStopped(activity: Activity) {
            }
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppActivityManager.add(activity)
            }
            override fun onActivityResumed(activity: Activity) {
            }

        })
    }

    open fun onMainProcessInit() {}

    /**
     * 其他进程初始化，[processName] 进程名
     */
    open fun onOtherProcessInit(processName: String) {}



}