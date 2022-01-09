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
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemCommentBinding
import com.ajkune.professional.databinding.ItemProductBinding
import com.ajkune.professional.utilities.data.Constants.dpToPx
import com.ajkune.professional.utilities.extensions.loadUrl
import com.ajkune.professional.utilities.helpers.Screen

class CommentsAdapter() : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(),
    BindableAdapter<List<Comment>> {

    var items: List<Comment> = listOf()

    override fun setData(data: List<Comment>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCommentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_comment, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CommentsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)

        if (position == items.size - 1){
            holder.binding.view7.visibility = View.GONE
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder( val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment, position: Int) {

            comment.user?.imageName?.let {
                binding.imgUser.loadUrl(it)
            }
            binding.txtMessageDescription.text = comment.comment
            binding.txtUserName.text = comment.user?.name
        }
    }
}
