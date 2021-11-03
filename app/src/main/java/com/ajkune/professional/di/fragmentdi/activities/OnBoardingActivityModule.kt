package com.ajkune.professional.di.fragmentdi.activities

import com.ajkune.professional.architecture.fragment.onboarding.LoginFragment
import com.ajkune.professional.architecture.fragment.onboarding.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class OnBoardingActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun bindWelcomeFragment(): WelcomeFragment
}