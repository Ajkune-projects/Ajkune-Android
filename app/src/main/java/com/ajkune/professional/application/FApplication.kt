package com.ajkune.professional.application

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.ajkune.professional.di.DaggerAppComponent
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.ajkune.professional.utilities.helpers.ConnectionLiveData
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class FApplication : Application(), HasActivityInjector, HasSupportFragmentInjector,
    HasServiceInjector {


    @Inject
    lateinit var baseAccountManager: BaseAccountManager

    override fun onCreate() {
        super.onCreate()


        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        //baseAccountManager.start()

    }


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun activityInjector() = activityInjector
    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector
    override fun serviceInjector() = serviceInjector

    fun setConnectivityListener(listener: ConnectionLiveData.ConnectivityReceiverListener) {
        ConnectionLiveData.connectivityReceiverListener = listener
    }


}