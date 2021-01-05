package com.eju.cache

import java.io.Serializable

interface Cache<K,V> {

    fun size():Long

    fun maxSize():Long

    fun remove(key:K)

    fun put(key:K, value:V)

    fun get(key:K):V?

    fun clear()

}