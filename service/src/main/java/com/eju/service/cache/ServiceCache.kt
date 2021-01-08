package com.eju.service.cache

import com.eju.cache.DiskCache
import com.eju.cache.MemoryCache
import com.eju.service.BaseResult
import com.eju.service.ServiceUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.Serializable
import java.util.concurrent.TimeUnit

object ServiceCache{


    var maxCountOfMemoryCacheObj=30

    var supportMemoryCache=false

    private val diskCache:DiskCache<CacheBlock<*>> by lazy {
        DiskCache<CacheBlock<*>>(ServiceUtil.application)
    }

    private val memoryCache: MemoryCache<String,CacheBlock<*>> by lazy {
        MemoryCache<String,CacheBlock<*>>(
            objSizeCalculator = { _, _ ->1},
            maxSize = maxCountOfMemoryCacheObj
        )
    }

    private fun <T> put(key:String,data:T,cachedTime:Long,cachedTimeUnit: TimeUnit){
        val expiredTime= if(cachedTime==CacheConfig.CACHED_FOREVER)
            CacheConfig.CACHED_FOREVER else  System.currentTimeMillis()+cachedTimeUnit.toMillis(cachedTime)
        val cacheBlock=CacheBlock(data,expiredTime)
        if(supportMemoryCache){
            memoryCache.put(key,cacheBlock)   //内存缓存
        }
        diskCache.put(key,cacheBlock)          //磁盘缓存
    }

    private fun <T> get(key:String):T?{
        var data:T?=null
        if(supportMemoryCache){
            val cacheBlock=memoryCache.get(key) as? CacheBlock<T>?
            if(cacheBlock!=null){     //内存缓存存在
                if(cacheBlock.isOutOfDate()){   //内存缓存过期
                    memoryCache.remove(key)
                }else{
                    data=cacheBlock.data
                }
            }
        }
        if(data==null){
            val cacheBlock=diskCache.get(key) as? CacheBlock<T>?
            if(cacheBlock!=null){
                if(cacheBlock.isOutOfDate()){   //磁盘缓存过期
                    diskCache.remove(key)
                }else{
                    memoryCache.put(key,cacheBlock)   //内存缓存
                    data=cacheBlock.data
                }
            }
        }
        return data

    }




    fun <T> getData(key:String, cacheConfig: CacheConfig =CacheConfig(),block:suspend ()-> T): Flow<T> {
        return flow {
            when(cacheConfig.cacheStrategy){
                CacheStrategy.NONE ->{
                    emit(block())   //直接调用接口
                }
                CacheStrategy.USE_CACHE ->{
                    val data= get<T>(key)
                    emit(data?:(block()).also {   //有缓存使用缓存 没有再去调用接口 同时缓存接口数据
                        put(key,it,cacheConfig.cachedTime,cacheConfig.cachedTimeUnit)
                    })
                }
                CacheStrategy.FIRST_CACHE_THEN_REMOTE ->{
                    val data= get<T>(key)
                    data?.let {
                        emit(it)   //使用缓存
                    }
                    emit((block()).also {  //调用接口 同时缓存接口数据
                        put(key,it,cacheConfig.cachedTime,cacheConfig.cachedTimeUnit)
                    })
                }
            }
        }
    }
}