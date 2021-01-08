package com.eju.service.cache

import com.eju.service.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.Serializable
import java.util.concurrent.TimeUnit


data class CacheConfig(
    val cachedTime:Long = CACHED_FOREVER,
    val cachedTimeUnit :TimeUnit= TimeUnit.MILLISECONDS,
    val cacheStrategy: CacheStrategy=CacheStrategy.FIRST_CACHE_THEN_REMOTE
){
    companion object{
        const val CACHED_FOREVER=-1L
    }
}

enum class CacheStrategy{
    NONE,
    USE_CACHE,
    FIRST_CACHE_THEN_REMOTE
}


class CacheBlock<T>(
    val data:T,
    private val expiredTime:Long,
): Serializable {

    fun isOutOfDate():Boolean{
        return expiredTime!=CacheConfig.CACHED_FOREVER&&(System.currentTimeMillis()-expiredTime>0)
    }
}


