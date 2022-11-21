package com.myapp.logistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myapp.logistics.R
import com.myapp.logistics.databinding.ItemDriverBinding
import com.myapp.logistics.model.Driver

class DriversAdapter : ListAdapter<Driver, DriversAdapter.ViewHolder>(driverItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_driver, parent, false)
        val binding = ItemDriverBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(private val binding: ItemDriverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Driver) {
            with(binding) {
                tvDriverName.text = item.fio.toString()
                tvDriverCar.text = item.car.toString()
                tvDriverPhone.text = item.phoneNumber.toString()
            }
        }
    }

    companion object {
        fun driverItemDiffUtil() = object : DiffUtil.ItemCallback<Driver>() {
            override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean = oldItem.fio == newItem.fio && oldItem.car == newItem.car && oldItem.phoneNumber == newItem.phoneNumber
        }
    }
}
