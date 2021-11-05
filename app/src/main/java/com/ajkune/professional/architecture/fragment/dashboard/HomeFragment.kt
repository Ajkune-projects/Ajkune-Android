package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.dashboard.HomeViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.HomeFragmentBinding
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    lateinit var binding : HomeFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]
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