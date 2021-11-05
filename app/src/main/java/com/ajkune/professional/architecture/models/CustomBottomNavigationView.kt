package com.ajkune.professional.architecture.models

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.ajkune.professional.R
import com.google.android.material.navigation.NavigationView

class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NavigationView(context, attrs, defStyleAttr){

    init {
        inflate(context, R.layout.custom_navigation_bottom_layout, this)
        setBackgroundColor(Color.WHITE)
    }

}