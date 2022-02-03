package com.ajkune.professional.architecture.activities

import android.content.Intent
import android.content.res.Configuration
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
import java.util.*
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

        if (baseAccountManager.language != null && baseAccountManager.language != ""){
            val current = baseAccountManager.language

            when (current.toString()) {
                "en_GB" -> {
                    setLanguage("en_GB")
                }
                "de" -> {
                    setLanguage("de")
                }
                "fr" -> {
                    setLanguage("fr")
                }
                "it" -> {
                    setLanguage("it")
                }
                else -> {
                    setLanguage("en_GB")
                }
            }
        }else{
            setLanguage("en_GB")
        }

        Handler(Looper.getMainLooper()).postDelayed({
            var intent: Intent? = null
            if (baseAccountManager.isLogged()){
                intent = Intent(this@MainActivity, DashboardActivity::class.java)
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

    private fun setLanguage(languageCode: String) {
        val newLocale = Locale(languageCode)
        val config = Configuration(this.resources.configuration)
        Locale.setDefault(newLocale)
        config.setLocale(newLocale)
        this.resources.updateConfiguration(
            config,
            this.resources.displayMetrics
        )
    }
}