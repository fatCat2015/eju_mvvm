package com.eju.architecture.router

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

object RouterPath {
    object Module1{
        const val list="/module1/list"
        const val multi="/module1/multi"
    }
    object Module2{
        const val main="/module2/main"
    }



    fun createPathUri(path:String):Uri{
        return Uri.parse("eju://mobile.app.yilou${path}")
    }

    fun createPathUri1(path:String):Uri{
        return Uri.parse("http://www.jiandanhome.com${path}")
    }
}

object RouterExtras{

}

fun Postcard.navigation(fragment: Fragment,requestCode:Int){
    fragment.activity?.let {activity->
        LogisticsCenter.completion(this)
        fragment.startActivityForResult(
            Intent(activity,destination).putExtras(extras)
            ,requestCode)
    }

}