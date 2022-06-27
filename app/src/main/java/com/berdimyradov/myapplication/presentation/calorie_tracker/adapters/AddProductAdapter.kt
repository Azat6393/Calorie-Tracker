package com.berdimyradov.myapplication.presentation.calorie_tracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.berdimyradov.myapplication.R
import com.berdimyradov.myapplication.databinding.AddProductItemBinding
import com.berdimyradov.myapplication.domain.model.Item

class AddProductAdapter(val listener: OnItemClickListener) :
    ListAdapter<Item, AddProductAdapter.AddProductViewHolder>(DiffCallBack) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddProductViewHolder {
        context = parent.context
        return AddProductViewHolder(
            AddProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddProductViewHolder, position: Int) {
        val item = getItem(position)
        item.let {
            holder.bind(it)
        }
    }

    inner class AddProductViewHolder(private val binding: AddProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                addProductNameTv.setOnClickListener {
                    productInfo.productInfoLayout.layoutAnimation =
                        AnimationUtils.loadLayoutAnimation(
                            context,
                            R.anim.layout_animation
                        )
                    productInfo.productInfoLayout.isVisible =
                        !productInfo.productInfoLayout.isVisible
                }
                addProductIv.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        item.let {
                            listener.addProduct(item)
                        }
                    }
                }
            }
        }

        fun bind(item: Item) {
            binding.apply {
                addProductNameTv.text = item.name
                productInfo.apply {
                    productCaloriesValueTv.text = item.calories.toString()
                    productFatValueTv.text = item.fat_total_g.toString()
                    productProteinValueTv.text = item.protein_g.toString()
                }
            }
        }
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return newItem.name == oldItem.name
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun addProduct(item: Item)
    }
}