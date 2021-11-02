package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.onboarding.LoginViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.LoginFragmentBinding
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    lateinit var binding : LoginFragmentBinding

    @Inject
    lateinit var viewModelFactory : AjkuneViewModelFactory

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[LoginViewModel::class.java]
        initBaseFunctions()
    }

    override fun onLoad() {
    }

    override fun onError() {
    }

    override fun onClickEvents() {
    }

    override fun setToolbar() {
    }
}