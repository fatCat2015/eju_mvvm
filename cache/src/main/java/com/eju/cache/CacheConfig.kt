package com.eju.cache

import java.io.File
import java.util.concurrent.TimeUnit

data class CacheConfig (
    val cachedTime:Long,
    val cachedTimeUnit: TimeUnit,
){
    companion object{
        const val CACHED_FOREVER=0L

        val DEFAULT=CacheConfig(CACHED_FOREVER,TimeUnit.MILLISECONDS)
    }
}

enum class CacheStrategy{
    NONE,
    USE_CACHE_IF_REMOTE_FAILED,
    USE_CACHE_IF_EXISTS,
    FIRST_CACHE_THEN_REMOTE,
}