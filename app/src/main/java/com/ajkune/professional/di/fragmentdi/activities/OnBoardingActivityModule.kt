package com.ajkune.professional.di.fragmentdi.activities

import com.ajkune.professional.architecture.fragment.dashboard.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class OnBoardingActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment
}