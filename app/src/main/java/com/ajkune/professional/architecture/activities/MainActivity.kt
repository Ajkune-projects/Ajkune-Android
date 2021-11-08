package com.ajkune.professional.architecture.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R
import com.ajkune.professional.base.activity.BaseActivity
import com.ajkune.professional.databinding.ActivityMainBinding
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import javax.inject.Inject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    private val SPLASH_TIME_OUT : Long  = 1000

    @Inject
    lateinit var baseAccountManager: BaseAccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBaseFunctions()
    }

    override fun onLoad() {

        Handler(Looper.getMainLooper()).postDelayed({
            var intent: Intent? = null
            if (baseAccountManager.isLogged()){
                intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
            }else{
                intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
            }


            startActivity(intent)
            finish()

        }, SPLASH_TIME_OUT)
    }

    override fun onError() {
    }

    override fun onClickEvents() {
    }

    override fun setToolbar() {
    }
}