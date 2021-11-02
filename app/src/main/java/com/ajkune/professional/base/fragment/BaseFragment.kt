package com.ajkune.professional.base.fragment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ajkune.professional.base.abstractactivity.BaseFunctions
import com.ajkune.professional.base.abstractactivity.LifeCycleOfActivity
import com.ajkune.professional.base.activity.BaseActivity
import dagger.android.support.AndroidSupportInjection
import java.lang.Exception

abstract class BaseFragment : Fragment(), BaseFunctions {

    var supportActionBar: ActionBar? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun showLoader() {
        (activity as BaseActivity). loader.show()
    }

    fun hideLoader() {
        (activity as BaseActivity).loader.hide()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar = (activity as AppCompatActivity).supportActionBar
        try {
            //toolbar = activity?.toolbar
        }catch (e: Exception){

        }
        supportActionBar?.hide()

    }

    fun setLifecycleListener(listener: LifeCycleOfActivity?){
        if (activity is BaseActivity){
            ( activity as BaseActivity).let {
                it.setListener(listener)
            }
        }
    }

}