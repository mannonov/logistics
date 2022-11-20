package com.myapp.logistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myapp.logistics.R
import com.myapp.logistics.databinding.ItemLoadBinding
import com.myapp.logistics.model.Load

class LoadsAdapter : ListAdapter<Load, LoadsAdapter.ViewHolder>(loadDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load, parent, false)
        val binding = ItemLoadBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(private val binding: ItemLoadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Load) {
            binding.apply {
                binding.tvPointA.text = item.aPoint?.address.toString()
                binding.tvPointB.text = item.bPoint?.address.toString()
                binding.tvCustomer.text = item.customer.toString()
            }
        }
    }

    companion object {
        fun loadDiffUtil() = object : DiffUtil.ItemCallback<Load>() {
            override fun areItemsTheSame(oldItem: Load, newItem: Load): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Load, newItem: Load): Boolean = oldItem.aPoint == newItem.aPoint && oldItem.bPoint == newItem.bPoint && oldItem.customer == newItem.customer
        }
    }
}
