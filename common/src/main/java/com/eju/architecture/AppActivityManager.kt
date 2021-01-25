package com.eju.architecture

import android.app.Activity

object AppActivityManager {
    private val mActivityList = mutableListOf<Activity>()

    internal fun add(activity: Activity) = mActivityList.add(activity)

    internal fun remove(activity: Activity) = mActivityList.remove(activity)

    fun isEmpty(): Boolean {
        return mActivityList.isEmpty()
    }

    fun getLast(clazz: Class<Activity>): Activity? {
        return mActivityList.findLast { it.javaClass == clazz }
    }

    fun getFirst(clazz: Class<Activity>): Activity? {
        return mActivityList.find { it.javaClass == clazz }
    }

    fun current(): Activity? {
        return mActivityList.lastOrNull()
    }

    fun finishCurrentActivity() {
        current()?.finish()
    }

    fun finishAllActivity() {
        mActivityList.forEach {
            it.finish()
        }
        mActivityList.clear()
    }

}