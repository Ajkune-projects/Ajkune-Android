package com.ajkune.professional.architecture.fragment.dashboard

import android.content.res.Configuration
import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.dashboard.LanguageViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.databinding.LanguageFragmentBinding
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*
import javax.inject.Inject

class LanguageFragment : BaseFragment() {

    lateinit var binding: LanguageFragmentBinding

    var isClickedLanguage: Boolean = false

    @Inject
    lateinit var baseAccountManager: BaseAccountManager

    companion object {
        fun newInstance() = LanguageFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.language_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
        initBaseFunctions()
    }

    override fun onLoad() {

        val current = resources.configuration.locale

        when (current.toString()) {
            "en_GB" -> {
                binding.txtCurrentLanguage.text = "English"
                binding.txtEnglish.setTextColor(requireContext().getColor(R.color.cl_a8466f))
            }
            "de" -> {
                binding.txtCurrentLanguage.text = "Deutsch"
                binding.txtDeutsch.setTextColor(requireContext().getColor(R.color.cl_a8466f))
            }
            "fr" -> {
                binding.txtCurrentLanguage.text = "French"
                binding.txtFrench.setTextColor(requireContext().getColor(R.color.cl_a8466f))
            }
            "it" -> {
                binding.txtCurrentLanguage.text = "Italian"
                binding.txtIntalian.setTextColor(requireContext().getColor(R.color.cl_a8466f))
            }
            else -> {
                binding.txtCurrentLanguage.text = "English"
                binding.txtEnglish.setTextColor(requireContext().getColor(R.color.cl_a8466f))
            }
        }
    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.txtCurrentLanguage.setOnClickListener {
            if (isClickedLanguage) {
                isClickedLanguage = false
                binding.cvLanguage.visibility = View.GONE
            } else {
                binding.cvLanguage.visibility = View.VISIBLE
                isClickedLanguage = true
            }
        }

        binding.txtEnglish.setOnClickListener {
            isClickedLanguage = false
            binding.cvLanguage.visibility = View.GONE
            binding.txtCurrentLanguage.text = "English"
            setLanguage("en_GB")
            baseAccountManager.language = "en_GB"
            restartActivity()
        }
        binding.txtDeutsch.setOnClickListener {
            isClickedLanguage = false
            binding.cvLanguage.visibility = View.GONE
            binding.txtCurrentLanguage.text = "Deutsch"
            setLanguage("de")
            baseAccountManager.language = "de"
            restartActivity()
        }
        binding.txtFrench.setOnClickListener {
            isClickedLanguage = false
            binding.cvLanguage.visibility = View.GONE
            binding.txtCurrentLanguage.text = "French"
            setLanguage("fr")
            baseAccountManager.language = "fr"
            restartActivity()
        }
        binding.txtIntalian.setOnClickListener {
            isClickedLanguage = false
            binding.cvLanguage.visibility = View.GONE
            binding.txtCurrentLanguage.text = "Italian"
            setLanguage("it")
            baseAccountManager.language = "it"
            restartActivity()
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
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

    private fun restartActivity() {
        val intent = requireActivity().intent
        startActivity(intent)
        requireActivity().finish()
    }
}