package com.eju.module2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.base.SimpleActivity
import com.eju.architecture.router.RouterPath
import kotlinx.android.synthetic.main.activity_main1.*

@Route(path = RouterPath.Module2.main)
class MainActivity : SimpleActivity(R.layout.activity_main1) {

    @Autowired
    @JvmField
    var name:String? = null
    override fun setListeners() {
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv2.text="${tv2.text}\n${name}\n${intent.getStringExtra(ARouter.RAW_URI)}"
    }


}
