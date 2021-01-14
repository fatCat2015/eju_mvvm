package com.eju.demo.view

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.eju.aop.annotation.AvoidMultiExecutions
import com.eju.aop.annotation.Event
import com.eju.aop.event.EventParam
import com.eju.aop.event.EventUploadProxy
import com.eju.aop.event.IEventUpload
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.observe
import com.eju.architecture.setUpWithViewPager2
import com.eju.architecture.widget.PaletteHelper
import com.eju.architecture.widget.SimpleFragmentAdapter2
import com.eju.demo.R
import com.eju.demo.databinding.ActivityTest2Binding
import com.eju.demo.viewmodel.TestViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_test2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : BaseActivity<TestViewModel,ActivityTest2Binding>(R.layout.activity_test2) {

    override fun setListeners() {
        bt1.setOnClickListener {
        }
    }



    override fun initData(savedInstanceState: Bundle?) {
        val titles=ArrayList<String>()
        val fragment=ArrayList<Fragment>().apply {
            repeat(5){
                add(DemoFragment.newInstance(it))
                titles.add("item${it}")
            }
        }
        viewPager.orientation=ViewPager2.ORIENTATION_VERTICAL
        viewPager.adapter=SimpleFragmentAdapter2(fragment,this)
        viewPager.offscreenPageLimit=fragment.size-1
        tabLayout.setUpWithViewPager2(viewPager, titles)
    }



}