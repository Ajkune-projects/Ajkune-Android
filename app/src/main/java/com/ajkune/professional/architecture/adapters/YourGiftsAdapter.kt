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
import com.ajkune.professional.architecture.models.Comment
import com.ajkune.professional.architecture.models.Gift
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemCommentBinding
import com.ajkune.professional.databinding.ItemProductBinding
import com.ajkune.professional.databinding.ItemYourGiftsBinding
import com.ajkune.professional.utilities.data.Constants.dpToPx
import com.ajkune.professional.utilities.extensions.loadUrl
import com.ajkune.professional.utilities.helpers.Screen

class YourGiftsAdapter() : RecyclerView.Adapter<YourGiftsAdapter.ViewHolder>(),
    BindableAdapter<ArrayList<Gift>> {

    var items: ArrayList<Gift> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: ArrayList<Gift>) {
        items.clear()
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourGiftsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemYourGiftsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_your_gifts, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: YourGiftsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder( val binding: ItemYourGiftsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gift: Gift, position: Int) {
                binding.txtGiftName.text = gift.status
        }
    }
}
