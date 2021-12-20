package com.ajkune.professional.di.fragmentdi.activities

import com.ajkune.professional.architecture.fragment.dashboard.*
import com.ajkune.professional.architecture.fragment.onboarding.LoginFragment
import com.ajkune.professional.architecture.fragment.onboarding.RegisterFragment
import com.ajkune.professional.databinding.AccountFragmentBinding
import com.ajkune.professional.databinding.HomeFragmentBinding
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class DashboardActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun bindAppointmentFragment(): AppointmentFragment

    @ContributesAndroidInjector
    internal abstract fun bindGifsFragment(): GifsFragment

    @ContributesAndroidInjector
    internal abstract fun bindOffersFragment(): OffersFragment

    @ContributesAndroidInjector
    internal abstract fun bindAccountFragment(): AccountFragment

    @ContributesAndroidInjector
    internal abstract fun bindProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    internal abstract fun bindMyProfileFragment(): MyProfileFragment

    @ContributesAndroidInjector
    internal abstract fun bindAppointmentDetailsFragment(): AppointmentDetailsFragment
}