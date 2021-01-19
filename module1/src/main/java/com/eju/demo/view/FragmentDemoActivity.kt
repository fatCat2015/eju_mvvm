package com.eju.demo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.eju.architecture.base.SimpleActivity
import com.eju.architecture.setUpWithViewPager2
import com.eju.architecture.widget.SimpleFragmentAdapter2
import com.eju.demo.R
import kotlinx.android.synthetic.main.activity_fragment_demo.*

class FragmentDemoActivity : SimpleActivity(R.layout.activity_fragment_demo) {
    override fun setListeners() {
    }

    override fun initData(savedInstanceState: Bundle?) {

        val titles=ArrayList<String>()
        val fragment=ArrayList<Fragment>().apply {
            repeat(5){
                add(DemoFragment.newInstance(it))
                titles.add("item${it}")
            }
        }
//        viewPager.orientation= ViewPager2.ORIENTATION_VERTICAL
        viewPager.adapter= SimpleFragmentAdapter2(fragment,this)
        viewPager.offscreenPageLimit=fragment.size-1
        tabLayout.setUpWithViewPager2(viewPager, titles)
    }

}