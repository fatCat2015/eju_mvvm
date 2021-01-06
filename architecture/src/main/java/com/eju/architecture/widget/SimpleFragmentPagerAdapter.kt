package com.eju.architecture.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SimpleFragmentPagerAdapter(private val fragmentList: List<Fragment>, fm: FragmentManager, var titles:List<String>?=null)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(titles==null&&(titles?.size!=fragmentList.size)){
            return super.getPageTitle(position)
        }
        return titles?.get(position)
    }
}
