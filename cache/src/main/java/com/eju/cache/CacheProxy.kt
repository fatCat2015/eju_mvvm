package com.eju.cache

import android.content.Context
import com.eju.cache.core.DiskCache
import com.eju.cache.core.MemoryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
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
            objSizeCalculator = { _, _ -> 1 },
            maxSize = maxCountOfMemoryCacheObj
        )
    }

    private fun <T> put(key:String,data:T,cachedTime:Long,cachedTimeUnit: TimeUnit){
        val expiredTime= if(cachedTime== CacheConfig.CACHED_FOREVER)
            CacheConfig.CACHED_FOREVER else  System.currentTimeMillis()+cachedTimeUnit.toMillis(cachedTime)
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

    fun <T> getData(key:String,cacheConfig: CacheConfig,cacheStrategy: CacheStrategy,block: suspend ()->T):Flow<T>{
        return when(cacheStrategy){
            CacheStrategy.USE_CACHE_IF_REMOTE_FAILED ->{
                flow {
                    emit(useCacheIfRemoteFailed(key,cacheConfig,block))
                }
            }
            CacheStrategy.USE_CACHE_IF_EXISTS ->{
                flow {
                    emit(useCacheIfExists(key,cacheConfig,block))
                }
            }
            CacheStrategy.FIRST_CACHE_THEN_REMOTE ->{
                firstCacheThenRemote(key,cacheConfig,block)
            }
            else ->{
                flow {
                    emit(justRemote(key,cacheConfig,block))
                }
            }
        }
    }


    /**
     * 不使用缓存
     */
    suspend fun <T> justRemote(key:String,cacheConfig: CacheConfig, block: suspend ()->T):T{
        return block().also {
            put(key, it, cacheConfig.cachedTime, cacheConfig.cachedTimeUnit)
        }
    }


    /**
     * 1.block()执行成功 返回data
     * 2.block()执行失败 尝试获取并返回data
     */
    suspend fun <T> useCacheIfRemoteFailed(key:String,cacheConfig: CacheConfig, block: suspend ()->T):T{
        return try {
            block().also {
                put(key, it, cacheConfig.cachedTime, cacheConfig.cachedTimeUnit)
            }
        } catch (e: Throwable) {
            val data= get<T>(key)
            data?:throw e
        }
    }


    /**
     * 1.有缓存并且缓存未过期使用缓存 不会执行block
     * 2.无缓存或者缓存过期 执行block 返回data
     */
    suspend fun <T> useCacheIfExists(key:String,cacheConfig: CacheConfig, block: suspend ()->T):T{
        val data= get<T>(key)
        return data?:(block()).also {   //有缓存使用缓存 没有再去调用接口 同时缓存接口数据
            put(key, it, cacheConfig.cachedTime, cacheConfig.cachedTimeUnit)
        }
    }


    /**
     * 返回一个Flow
     * 1.有缓存并且缓存未过期 发射缓存data
     * 2.执行block 发射 data
     */
    fun <T> firstCacheThenRemote(key:String, cacheConfig: CacheConfig, block:suspend ()->T): Flow<T> {
        return flow {
            val data = get<T>(key)
            data?.let { cachedData ->
                emit(cachedData)   //使用缓存
            }
            emit((block()).also {  //调用接口 同时缓存接口数据
                put(key, it, cacheConfig.cachedTime, cacheConfig.cachedTimeUnit)
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