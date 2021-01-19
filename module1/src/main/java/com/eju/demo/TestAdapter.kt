package com.eju.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.eju.demo.api.HelpDetail
import com.eju.demo.api.Message
import com.eju.demo.databinding.ItemTestBinding

class TestAdapter(private val list:List<Message>?):RecyclerView.Adapter<TestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_test,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val item=list!![position]
        val binding=holder.binding as ItemTestBinding
        binding.item=item
        binding.executePendingBindings()
    }
}

class TestViewHolder(val binding:ViewDataBinding):RecyclerView.ViewHolder(binding.root)