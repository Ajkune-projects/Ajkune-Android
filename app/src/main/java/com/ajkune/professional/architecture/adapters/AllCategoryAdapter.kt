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
import com.ajkune.professional.databinding.ItemAllCategoriesBinding
import com.ajkune.professional.databinding.ItemCategoryBinding

class AllCategoryAdapter(val listener : Listener) : RecyclerView.Adapter<AllCategoryAdapter.ViewHolder>(),
    BindableAdapter<List<Category>> {

    var items: List<Category> = listOf()


    override fun setData(data: List<Category>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemAllCategoriesBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_all_categories, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AllCategoryAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)

    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder( val binding: ItemAllCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: Category, position: Int) {
            binding.txtCategoryName.text = category.name

            binding.clCategory.setOnClickListener {
                listener.onCategoryClicked(category, position)
            }
        }
    }

    interface Listener{
        fun onCategoryClicked(category: Category, position: Int)
    }
}
