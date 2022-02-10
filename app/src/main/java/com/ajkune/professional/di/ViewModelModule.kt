package com.ajkune.professional.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajkune.professional.architecture.fragment.dashboard.FilterProductsViewModel
import com.ajkune.professional.architecture.fragment.dashboard.MyProfileFragment
import com.ajkune.professional.architecture.viewmodels.dashboard.*
import com.ajkune.professional.architecture.viewmodels.onboarding.*
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

    @Binds
    @IntoMap
    @ViewModelKey(AppointmentViewModel::class)
    abstract fun bindAppointmentViewModel(appointmentViewModel: AppointmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GifsViewModel::class)
    abstract fun bindGifsViewModel(gifsViewModel: GifsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(OffersViewModel::class)
    abstract fun bindOffersViewModel(offersViewModel: OffersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    abstract fun bindProductDetailsViewModel(productDetailsViewModel: ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyProfileViewModel::class)
    abstract fun bindMyProfileViewModel(myProfileViewModel: MyProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AppointmentDetailsViewModel::class)
    abstract fun bindAppointmentDetailsViewModel(appointmentDetailsViewModel: AppointmentDetailsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SuccessAppointmentViewModel::class)
    abstract fun bindSuccessAppointmentViewModel(successAppointmentViewModel: SuccessAppointmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterProductsViewModel::class)
    abstract fun bindFilterProductsViewModel(filterProductsViewModel: FilterProductsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel::class)
    abstract fun bindForgotPasswordViewModel(forgotPasswordViewModel: ForgotPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResetPasswordViewModel::class)
    abstract fun bindResetPasswordViewModel(resetPasswordViewModel: ResetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAddressViewModel::class)
    abstract fun bindAddAddressViewModel(addAddressViewModel: AddAddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LuckyWheelViewModel::class)
    abstract fun bindLuckyWheelViewModel(luckyWheelViewModel: LuckyWheelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(YourGiftsViewModel::class)
    abstract fun bindYourGiftsViewModel(yourGiftsViewModel: YourGiftsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GiftWonViewModel::class)
    abstract fun bindGiftWonViewModel(giftWonViewModel: GiftWonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel



    /**baseFactory for all [ViewModel]*/
    @Binds
    abstract fun bindViewModelFactory(factory: AjkuneViewModelFactory): ViewModelProvider.Factory
}