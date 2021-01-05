package com.eju.cache

import android.graphics.Bitmap
import android.util.Log

class BitmapCache<K>(maxSize:Int):MemoryCache<K,Bitmap>(
    objSizeCalculator={key,value ->(value.allocationByteCount)},
    maxSize=maxSize,
    onEntryRemoved = {evicted,_,oldValue,_->
        if(evicted){
            oldValue?.recycle()
        }
    }
)