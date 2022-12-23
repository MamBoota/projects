package com.example.currencyrate.presentation.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrate.R
import com.example.currencyrate.databinding.ItemCurencyBinding
import com.example.currencyrate.datasource.remotedatasource.data.AdapterData

class RecyclerAdapter(private val listener: RecyclerListener) :
    ListAdapter<AdapterData, RecyclerAdapter.RecyclerViewHolder>(
        AsyncDifferConfig.Builder(
            DiffUtilCallBack()
        ).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_curency, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val elem = currentList[position]
        holder.bind(elem, listener)
    }

    override fun getItemCount(): Int = currentList.size

    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCurencyBinding.bind(view)


        fun bind(data: AdapterData, listener: RecyclerListener) {
            binding.currencyName.text = data.name
            binding.currencyValue.text = data.value.toString()
            binding.addFavorite.setImageResource(
                if (data.isFavorite) {
                    R.drawable.ic_star_red
                } else {
                    R.drawable.ic_star_grey
                }
            )
            binding.addFavorite.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    private class DiffUtilCallBack : DiffUtil.ItemCallback<AdapterData>() {

        override fun areItemsTheSame(oldItem: AdapterData, newItem: AdapterData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: AdapterData, newItem: AdapterData): Boolean {
            return oldItem == newItem
        }

    }

    interface RecyclerListener {
        fun onClick(data: AdapterData)
    }

}

