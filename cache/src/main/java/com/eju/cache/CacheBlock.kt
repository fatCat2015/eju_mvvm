package com.eju.cache

import java.io.Serializable

class CacheBlock<T>(
    val data:T,
    private val expiredTime:Long,
): Serializable {

    companion object{
        const val CACHED_FOREVER=-1L
    }

    fun isOutOfDate():Boolean{
        return expiredTime!= CACHED_FOREVER &&(System.currentTimeMillis()-expiredTime>0)
    }
}


