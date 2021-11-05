package com.ajkune.professional.di.fragmentdi.activities

import com.ajkune.professional.architecture.fragment.dashboard.HomeFragment
import com.ajkune.professional.architecture.fragment.onboarding.LoginFragment
import com.ajkune.professional.architecture.fragment.onboarding.RegisterFragment
import com.ajkune.professional.databinding.HomeFragmentBinding
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class DashboardActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment
}