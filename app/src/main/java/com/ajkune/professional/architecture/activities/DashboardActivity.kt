package com.ajkune.professional.architecture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ajkune.professional.R
import com.ajkune.professional.base.activity.BaseActivity
import com.ajkune.professional.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.custom_navigation_bottom_layout.view.*

class DashboardActivity : BaseActivity() {

    lateinit var binding : ActivityDashboardBinding

    private val screens = mapOf<String, Map<String, Int>>(
        "HomeFragment" to mapOf<String, Int>(
            "text" to R.id.homeFragmentText,
            "icon" to R.id.homeFragmentIcon
        ),
        "AppointmentFragment" to mapOf<String, Int>(
            "text" to R.id.appointmentFragmentText,
            "icon" to R.id.appointmentFragmentIcon
        ),
        "GifsFragment" to mapOf<String, Int>(
            "text" to R.id.giftsFragmentText,
            "icon" to R.id.giftsFragmentIcon
        ),
        "OffersFragment" to mapOf<String, Int>(
            "text" to R.id.offersFragmentText,
            "icon" to R.id.offersFragmentIcon
        ),
        "AccountFragment" to mapOf<String, Int>(
            "text" to R.id.accountFragmentText,
            "icon" to R.id.accountFragmentIcon
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        setupNavigation()
        initBaseFunctions()
    }


    private fun setupNavigation(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment?
        NavigationUI.setupWithNavController(
            binding.dashboardNavigationView,
            navHostFragment!!.navController
        )

        binding.dashboardNavigationView.homeFragment.setOnClickListener {
            navHostFragment.navController.navigate(R.id.homeFragment)
            changeBottomFocusScreen("HomeFragment")
        }

        binding.dashboardNavigationView.appointmentFragment.setOnClickListener {
            navHostFragment.navController.navigate(R.id.appointmentFragment)
            changeBottomFocusScreen("AppointmentFragment")
        }

        binding.dashboardNavigationView.gifsFragment.setOnClickListener {
            navHostFragment.navController.navigate(R.id.gifsFragment)
            changeBottomFocusScreen("GifsFragment")
        }

        binding.dashboardNavigationView.offersFragment.setOnClickListener {
            navHostFragment.navController.navigate(R.id.offersFragment)
            changeBottomFocusScreen("OffersFragment")
        }

        binding.dashboardNavigationView.accountFragment.setOnClickListener {
            navHostFragment.navController.navigate(R.id.accountFragment)
            changeBottomFocusScreen("AccountFragment")
        }

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            changeBottomFocusScreen(destination.label.toString())
        }

    }

    override fun onLoad() {
    }

    override fun onError() {
    }

    override fun onClickEvents() {
    }

    override fun setToolbar() {
    }

    private fun changeBottomFocusScreen(to: String) {
        if (screens[to] != null) {
            val it = screens.entries.iterator()
            while (it.hasNext()) {
                val pair = it.next()
                val icon = pair.value["icon"] ?: R.id.homeFragmentIcon
                val text = pair.value["text"] ?: R.id.homeFragmentText
                if (pair.key == to) {
                    binding.dashboardNavigationView.findViewById<ImageView>(icon)
                        .setColorFilter(getColor(R.color.cl_a8466f))
                    binding.dashboardNavigationView.findViewById<TextView>(text)
                        .setTextColor(getColor(R.color.cl_a8466f))
                }else{
                    binding.dashboardNavigationView.findViewById<ImageView>(icon)
                        .setColorFilter(getColor(R.color.cl_8c93a9))
                    binding.dashboardNavigationView.findViewById<TextView>(text)
                        .setTextColor(getColor(R.color.cl_8c93a9))
                }
            }
        }

    }
}