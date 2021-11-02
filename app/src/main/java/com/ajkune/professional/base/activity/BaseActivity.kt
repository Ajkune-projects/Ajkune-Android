package com.ajkune.professional.base.activity

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ajkune.professional.application.FApplication
import com.ajkune.professional.base.abstractactivity.BaseFunctions
import com.ajkune.professional.base.abstractactivity.LifeCycleOfActivity
import com.ajkune.professional.utilities.helpers.ConnectionLiveData
import com.ajkune.professional.utilities.helpers.Loader
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class  BaseActivity : AppCompatActivity(), HasActivityInjector, HasSupportFragmentInjector,
    BaseFunctions,  ConnectionLiveData.ConnectivityReceiverListener{

    protected lateinit var connectionLiveData : ConnectionLiveData

    lateinit var connManager : ConnectivityManager

    var  isMetered : Boolean = false


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    lateinit var loader: Loader
    var isBlack: Boolean = false
    var mListener: LifeCycleOfActivity? = null

    override fun activityInjector() = activityInjector
    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        loader = Loader(supportFragmentManager, isBlack)

        connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, androidx.lifecycle.Observer { connection ->
            connection?.let {
            }
        })

        isMetered = connManager.isActiveNetworkMetered


    }

    override fun onResume() {
        super.onResume()
        FApplication().setConnectivityListener(this)
    }

    fun setListener(listener: LifeCycleOfActivity?) {
        this.mListener = listener
    }

    override fun onBackPressed() {
        mListener?.onBackPressedInActivity()
        super.onBackPressed()
    }

    override fun finish() {
        mListener?.onFinishActivity()
        super.finish()
    }

    override fun onNetworkConnectionChange(isConnected: Boolean) {

        if (!isConnected){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }else if (!isNetworkOnline(this)){
            Toast.makeText(this, "Internet may not be available!", Toast.LENGTH_SHORT).show()
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun isNetworkOnline(context: Context): Boolean {
        var isOnline = false
        try {
            val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                manager.getNetworkCapabilities(manager.activeNetwork) // need ACCESS_NETWORK_STATE permission
            isOnline =
                capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isOnline
    }
}