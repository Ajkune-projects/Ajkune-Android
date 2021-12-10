package com.ajkune.professional.architecture.fragment.dashboard

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.activities.OnBoardingActivity
import com.ajkune.professional.architecture.viewmodels.dashboard.AccountViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.AccountFragmentBinding
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import javax.inject.Inject

class AccountFragment : BaseFragment() {

    lateinit var binding : AccountFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    @Inject
    lateinit var baseAccountManager : BaseAccountManager

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.account_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[AccountViewModel::class.java]
        initBaseFunctions()
    }

    override fun onLoad() {
    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.txtLogout.setOnClickListener {
            baseAccountManager.delete()
            val intent = Intent(requireContext(), OnBoardingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            activity?.finish()
        }

        binding.txtProfile.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_myProfileFragment)
        }

        binding.txtLanguage.setOnClickListener {

        }
    }

    override fun setToolbar() {
    }
}