package com.ajkune.professional.di.fragmentdi.activities

import com.ajkune.professional.architecture.fragment.dashboard.ProductDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ProductDetailsModule {
    @ContributesAndroidInjector
    internal abstract fun bindProductDetailsFragment(): ProductDetailsFragment
}