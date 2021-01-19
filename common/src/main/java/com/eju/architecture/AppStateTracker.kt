package com.eju.architecture

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

object AppStateTracker {

    private val appStateObserverList= mutableListOf<AppStateObserver>()

    private var foregroundFlag=false

    fun isAppForeground()= foregroundFlag


    fun init() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object:DefaultLifecycleObserver{
            override fun onCreate(owner: LifecycleOwner) {
                appStateObserverList.forEach {
                    it.onAppCreate()
                }
            }

            override fun onResume(owner: LifecycleOwner) {
                foregroundFlag=true
                appStateObserverList.forEach {
                    it.onAppResume()
                }
            }

            override fun onStop(owner: LifecycleOwner) {
                foregroundFlag=false
                appStateObserverList.forEach {
                    it.onAppStop()
                }
            }
        })
    }

    fun addObserver(appStateObserver: AppStateObserver){
        if(!appStateObserverList.contains(appStateObserver)){
            appStateObserverList.add(appStateObserver)
        }
    }

    fun removeObserver(appStateObserver: AppStateObserver){
        appStateObserverList.remove(appStateObserver)
    }


}

interface AppStateObserver{
    fun onAppCreate(){

    }
    fun onAppResume()
    fun onAppStop()
}