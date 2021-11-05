package com.ajkune.professional.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajkune.professional.architecture.viewmodels.dashboard.HomeViewModel
import com.ajkune.professional.architecture.viewmodels.onboarding.LoginViewModel
import com.ajkune.professional.architecture.viewmodels.onboarding.RegisterViewModel
import com.ajkune.professional.architecture.viewmodels.onboarding.WelcomeViewModel
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.di.anotation.ViewModelKey
import com.ajkune.professional.di.fragmentdi.FragmentsViewModelModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module(includes = [FragmentsViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindWelcomeViewModel(welcomeViewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel


    /**baseFactory for all [ViewModel]*/
    @Binds
    abstract fun bindViewModelFactory(factory: AjkuneViewModelFactory): ViewModelProvider.Factory
}