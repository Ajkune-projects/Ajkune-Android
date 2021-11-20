package com.ajkune.professional.architecture.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkune.professional.R
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemCategoryBinding

class CategoryAdapter(val listener : Listener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(),
    BindableAdapter<List<Category>> {

    var items: List<Category> = listOf()

    var selectedPosition: Int? = null

    var isFirstTime: Boolean = true

    override fun setData(data: List<Category>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)

        if (isFirstTime){
            if (position == 0){
                holder.binding.txtCategoryName.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.cl_a8466f))
                holder.binding.view.visibility = View.VISIBLE
                isFirstTime = false
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder( val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: Category, position: Int) {
            binding.txtCategoryName.text = category.name

            if (selectedPosition != null) {
                if (selectedPosition == position) {
                    binding.txtCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_a8466f))
                    binding.view.visibility = View.VISIBLE
                } else {
                    binding.txtCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    binding.view.visibility = View.INVISIBLE
                }
            } else {
                binding.txtCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.view.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {
                listener.onCategoryClicked(category,position)
                selectedPosition =
                    if (selectedPosition == adapterPosition) adapterPosition else adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    interface Listener{
        fun onCategoryClicked(category: Category, position: Int)
    }
}
