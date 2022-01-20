package com.ajkune.professional.di.fragmentdi.activities

import com.ajkune.professional.architecture.fragment.onboarding.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class OnBoardingActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun bindWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    internal abstract fun bindRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun bindForgotPasswordFragment(): ForgotPasswordFragment

    @ContributesAndroidInjector
    internal abstract fun bindResetPasswordFragment(): ResetPasswordFragment

}