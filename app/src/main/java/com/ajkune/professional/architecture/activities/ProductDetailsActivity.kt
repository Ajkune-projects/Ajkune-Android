package com.ajkune.professional.architecture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ajkune.professional.R
import com.ajkune.professional.base.activity.BaseActivity

class ProductDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        initBaseFunctions()
    }

    override fun onLoad() {
    }

    override fun onError() {
    }

    override fun onClickEvents() {
    }

    override fun setToolbar() {

    }
}