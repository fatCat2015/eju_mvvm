package com.eju.cache

import java.io.Serializable

class CacheBlock<T>(
    val data:T,
    private val expiredTime:Long,
): Serializable {

    fun isOutOfDate():Boolean{
        return expiredTime!= CacheConfig.CACHED_FOREVER &&(System.currentTimeMillis()-expiredTime>0)
    }
}


