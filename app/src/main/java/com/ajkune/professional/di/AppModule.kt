package com.ajkune.professional.di

import android.app.Application
import android.content.Context
import com.ajkune.professional.di.anotation.ForServiceRest
import com.ajkune.professional.rest.ServiceRest
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.ajkune.professional.utilities.helpers.NetworkUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideNetworkUtil(context: Context) : NetworkUtil = NetworkUtil(context)

    @Singleton
    @Provides
    fun provideBaseAccountManager(context: Context) : BaseAccountManager = BaseAccountManager(context)

    @Singleton
    @Provides
    @ForServiceRest
    fun provideServiceREST(networkUtil: NetworkUtil, baseAccountManager: BaseAccountManager): ServiceRest = ServiceRest(networkUtil, baseAccountManager)

}
