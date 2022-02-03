package com.ajkune.professional.architecture.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemProductBinding
import com.ajkune.professional.utilities.data.Constants.dpToPx
import com.ajkune.professional.utilities.extensions.loadUrl
import com.ajkune.professional.utilities.helpers.Screen
import android.widget.LinearLayout

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ajkune.professional.R


class ProductsAdapter(val listener : Listener) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(),
    BindableAdapter<List<Product>> {

    var items: List<Product> = listOf()

    override fun setData(data: List<Product>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {
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
        fun bind(product: Product, position: Int) {
            binding.imgProduct.loadUrl(product.image)
            binding.txtProductDescription.text = product.name

            if(product.initialPrice.toDouble() > 0.00){
                binding.txtOriginalPrice.text = binding.root.context.getString(R.string.price, product.initialPrice)
                binding.txtOriginalPrice.visibility = View.VISIBLE
                binding.txtCurrentPrice.visibility = View.VISIBLE
                binding.txtSinglePrice.visibility = View.GONE
                binding.txtOriginalPrice.paintFlags = binding.txtOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.txtCurrentPrice.text = binding.root.context.getString(R.string.price, product.price)

            }else{
                binding.txtOriginalPrice.visibility = View.INVISIBLE
                binding.txtCurrentPrice.visibility = View.INVISIBLE
                binding.txtSinglePrice.visibility = View.VISIBLE
                binding.txtSinglePrice.text = binding.root.context.getString(R.string.price, product.price)
            }

            binding.ratingBar.rating = product.rating.toFloat()
            binding.root.setOnClickListener {
                listener.onProductClicked(product)
            }
        }
    }

    interface Listener{
        fun onProductClicked(product: Product)
    }
}
