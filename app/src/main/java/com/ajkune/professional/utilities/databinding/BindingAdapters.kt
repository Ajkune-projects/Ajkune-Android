package com.ajkune.professional.utilities.databinding

import android.app.Activity
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.utilities.extensions.isnotNull

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("data")
    fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
        if (recyclerView.adapter is BindableAdapter<*>) (recyclerView.adapter as BindableAdapter<T>).setData(data)
    }

    /**
     * Makes the View [View.INVISIBLE] unless the condition is met.
     */
    @Suppress("unused")
    @BindingAdapter("invisibleUnless")
    @JvmStatic fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * In [ProgressBar], [ProgressBar.setMax] must be called before [ProgressBar.setProgress].
     * By grouping both attributes in a BindingAdapter we can make sure the order is met.
     *
     * Also, this showcases how to deal with multiple API levels.
     */
    @BindingAdapter(value=["android:max", "android:progress"], requireAll = true)
    @JvmStatic fun updateProgress(progressBar: ProgressBar, max: Int, progress: Int) {
        progressBar.max = max
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, false)
        } else {
            progressBar.progress = progress
        }
    }

    @BindingAdapter("loseFocusWhen")
    @JvmStatic fun loseFocusWhen(view: EditText, condition: Boolean) {
        if (condition) view.clearFocus()
    }

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun bindImage(imageView: ImageView, url: Any?) {
        if (url.isnotNull()){
            if (url is String){
                //imageView.loadUrl(url)
            } else if (url is Int){
                imageView.setImageResource(url)
            }
        }
    }

    /** implementation popstate*/
    @JvmStatic
    @BindingAdapter("app:onBackClick")
    fun onBackClick(view: View, allow: Boolean) {

        if (allow) {
            val context = view.context

            view.setOnClickListener {
                if (it.findNavController().popBackStack()) {
                } else {
                    (context as Activity).finish()
                }
            }
        }
    }
}