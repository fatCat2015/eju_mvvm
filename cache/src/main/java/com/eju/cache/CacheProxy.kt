package com.eju.cache

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

class CacheProxy(
    private var context:Context,
    private var maxCountOfMemoryCacheObj:Int = 30,
    private var supportMemoryCache:Boolean = false,
){


    private val diskCache: DiskCache<CacheBlock<*>> by lazy {
        DiskCache<CacheBlock<*>>(context)
    }

    private val memoryCache: MemoryCache<String, CacheBlock<*>> by lazy {
        MemoryCache<String, CacheBlock<*>>(
            objSizeCalculator = { _, _ ->1},
            maxSize = maxCountOfMemoryCacheObj
        )
    }

    private fun <T> put(key:String,data:T,cachedTime:Long,cachedTimeUnit: TimeUnit){
        val expiredTime= if(cachedTime== CacheBlock.CACHED_FOREVER)
            CacheBlock.CACHED_FOREVER else  System.currentTimeMillis()+cachedTimeUnit.toMillis(cachedTime)
        val cacheBlock= CacheBlock(data, expiredTime)
        if(supportMemoryCache){
            memoryCache.put(key,cacheBlock)   //内存缓存
        }
        diskCache.put(key,cacheBlock)          //磁盘缓存
    }

    private fun <T> get(key:String):T?{
        var data:T?=null
        if(supportMemoryCache){
            val cacheBlock= memoryCache.get(key) as? CacheBlock<T>?
            if(cacheBlock!=null){     //内存缓存存在
                if(cacheBlock.isOutOfDate()){   //内存缓存过期
                    memoryCache.remove(key)
                }else{
                    data=cacheBlock.data
                }
            }
        }
        if(data==null){
            val cacheBlock= diskCache.get(key) as? CacheBlock<T>?
            if(cacheBlock!=null){
                if(cacheBlock.isOutOfDate()){   //磁盘缓存过期
                    diskCache.remove(key)
                }else{
                    if(supportMemoryCache){
                        memoryCache.put(key,cacheBlock)   //内存缓存
                    }
                    data=cacheBlock.data
                }
            }
        }
        return data
    }


    suspend fun <T> useCacheIfExits(key:String, cachedTime:Long= CacheBlock.CACHED_FOREVER, cachedTimeUnit: TimeUnit = TimeUnit.MILLISECONDS, block: suspend ()->T):T{
        val data= get<T>(key)
        return data?:(block()).also {   //有缓存使用缓存 没有再去调用接口 同时缓存接口数据
            put(key, it, cachedTime, cachedTimeUnit)
        }
    }


    fun <T> firstCacheThenRemote(key:String,
                                 cachedTime:Long= CacheBlock.CACHED_FOREVER,
                                 cachedTimeUnit: TimeUnit = TimeUnit.MILLISECONDS,
                                 block:suspend ()->T): Flow<T> {
        return flow {
            val data = get<T>(key)
            data?.let { cachedData ->
                emit(cachedData)   //使用缓存
            }
            emit((block()).also {  //调用接口 同时缓存接口数据
                put(key, it, cachedTime, cachedTimeUnit)
            })
        }
    }


    fun remove(key:String){
        if(supportMemoryCache){
            memoryCache.remove(key)
        }
        diskCache.remove(key)
    }

    fun clear(){
        if(supportMemoryCache){
            memoryCache.clear()
        }
        diskCache.clear()
    }

}