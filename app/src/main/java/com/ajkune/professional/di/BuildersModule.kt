package com.ajkune.professional.di

import com.ajkune.professional.architecture.activities.DashboardActivity
import com.ajkune.professional.architecture.activities.MainActivity
import com.ajkune.professional.architecture.activities.OnBoardingActivity
import com.ajkune.professional.architecture.activities.ProductDetailsActivity
import com.ajkune.professional.di.fragmentdi.activities.DashboardActivityModule
import com.ajkune.professional.di.fragmentdi.activities.OnBoardingActivityModule
import com.ajkune.professional.di.fragmentdi.activities.ProductDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [OnBoardingActivityModule::class])
    internal abstract fun bindOnBoardingActivity() : OnBoardingActivity

    @ContributesAndroidInjector(modules = [DashboardActivityModule::class])
    internal abstract fun bindDashboardActivity() : DashboardActivity

    @ContributesAndroidInjector(modules = [ProductDetailsModule::class])
    internal abstract fun bindProductDetailsActivity() : ProductDetailsActivity
}