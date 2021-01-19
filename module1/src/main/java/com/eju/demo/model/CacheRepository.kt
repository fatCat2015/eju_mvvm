package com.eju.demo.model

import com.eju.architecture.base.BaseRepository
import com.eju.cache.CacheConfig
import com.eju.cache.CacheStrategy
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CacheRepository :BaseRepository(){


    suspend fun useCacheIfRemoteFailed():HelpDetail{
        return fromCache {
            getApi(DemoService::class.java).getHelpDetail("58")
        }
    }

    suspend fun useCacheIfExists():HelpDetail{
        return fromCache(cacheConfig = CacheConfig.DEFAULT,cacheStrategy = CacheStrategy.USE_CACHE_IF_EXISTS) {
            getApi(DemoService::class.java).getHelpDetail("58")
        }
    }

    fun firstCacheThenRemote():Flow<HelpDetail>{
        return firstCacheThenRemote(cacheConfig = CacheConfig.DEFAULT) {
            getApi(DemoService::class.java).getHelpDetail("58")
        }
    }
}