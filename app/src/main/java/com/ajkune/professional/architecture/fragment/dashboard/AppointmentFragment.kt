
package com.ajkune.professional.architecture.fragment.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.SslError
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CalendarView
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R

import com.ajkune.professional.architecture.viewmodels.dashboard.AppointmentViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.databinding.AppointmentFragmentBinding
import kotlinx.android.synthetic.main.appointment_fragment.*


import android.widget.Toast

import android.widget.CalendarView.OnDateChangeListener
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AppointmentFragment :  BaseFragment() {

    @Inject
    lateinit var baseAccountManager : BaseAccountManager

    var languageCode : String = "de-CH"

    lateinit var binding : AppointmentFragmentBinding

    var url : String = ""

    companion object {
        fun newInstance() = AppointmentFragment()
    }

    private lateinit var viewModel: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.appointment_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        initBaseFunctions()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onLoad() {

        val current = baseAccountManager.language

        when (current.toString()) {
            "en_GB" -> {
                languageCode = "en-CH"
            }
            "en" ->{
                languageCode = "en-CH"
            }
            "de" -> {
                languageCode = "de-CH"
            }
            "fr" -> {
                languageCode = "fr-CH"
            }
            "it" -> {
                languageCode = "it-CH"
            }
            else -> {
                languageCode = "de-CH"
            }
        }

        url = "https://connect.shore.com/bookings/ajkune-professional-spreitenbach-nbz/locations?locale=$languageCode"

        val webView =  binding.webView
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        //this fixed for loading dynamilcy context
        webView.settings.domStorageEnabled = true

        webView.loadUrl(url)

        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler!!, error)
                handler.proceed()
            }
        }
    }

    override fun onError() {
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClickEvents() {

    }

    override fun setToolbar() {

    }

}