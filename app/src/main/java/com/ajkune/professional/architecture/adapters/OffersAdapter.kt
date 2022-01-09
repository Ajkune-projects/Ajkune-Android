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
import com.ajkune.professional.architecture.models.Offer
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemProductBinding
import com.ajkune.professional.utilities.data.Constants.dpToPx
import com.ajkune.professional.utilities.extensions.loadUrl
import com.ajkune.professional.utilities.helpers.Screen

class OffersAdapter(val listener : Listener) : RecyclerView.Adapter<OffersAdapter.ViewHolder>(),
    BindableAdapter<List<Offer>> {

    var items: List<Offer> = listOf()

    override fun setData(data: List<Offer>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: OffersAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
        val width = (Screen.width - dpToPx(40)) / 2
        val params = holder.binding.cvMain.layoutParams
        val paramsImage = holder.binding.cvImageHolder.layoutParams
        params.width = width
        paramsImage.height = width

    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder( val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(product: Offer, position: Int) {
            binding.imgProduct.loadUrl(product.image)
            binding.txtProductDescription.text = product.name
            binding.txtProductPrice.text = binding.root.context.getString(R.string.price, product.price)
            binding.ratingBar.rating = product.rating.toFloat()

            binding.root.setOnClickListener {
                listener.onOffersClicked(product)
            }
        }
    }

    interface Listener{
        fun onOffersClicked(product: Offer)
    }
}
