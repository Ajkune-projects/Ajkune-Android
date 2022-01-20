package com.ajkune.professional.architecture.fragment.onboarding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.fragment.dashboard.FilterProductsFragmentArgs
import com.ajkune.professional.architecture.viewmodels.onboarding.ResetPasswordViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.ResetPasswordFragmentBinding
import com.ajkune.professional.utilities.data.Constants
import javax.inject.Inject

class ResetPasswordFragment : BaseFragment() {

    lateinit var binding : ResetPasswordFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    var code : String = ""
    var email : String = ""

    companion object {
        fun newInstance() = ResetPasswordFragment()
    }

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.reset_password_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResetPasswordViewModel::class.java]
        getData()
        initBaseFunctions()
    }

    override fun onLoad() {
        viewModel.successChangedPsw.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(
                    context,
                    getString(R.string.password_changed_successfully),
                    Toast.LENGTH_LONG
                ).show()
                Constants.delay(2000) {
                    activity?.runOnUiThread {
                        findNavController().popBackStack(R.id.loginFragment, false)
                    }
                }
            }
        })
    }

    override fun onError() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClickEvents() {
        binding.btnReset.setOnClickListener {
            if (binding.etNewPassword.text!!.isEmpty()){
                Toast.makeText(context, getString(R.string.empty_password), Toast.LENGTH_LONG).show()
            }else if (binding.etConfirmNewPassword.text!!.isEmpty()){
                Toast.makeText(context, getString(R.string.register_empty_confirm_password), Toast.LENGTH_LONG).show()
            }else if (binding.etNewPassword.text.toString() != binding.etConfirmNewPassword.text.toString()){
                Toast.makeText(context, getString(R.string.register_password_not_match), Toast.LENGTH_LONG).show()
            }else{
                showLoader()
                val newPassword = binding.etNewPassword.text.toString()
                viewModel.resetPassword(email, newPassword, newPassword, code)
            }
        }

    }

    override fun setToolbar() {

    }

    private fun getData() {
        arguments?.let { bundle ->
            ResetPasswordFragmentArgs.fromBundle(bundle).let {
                if (it.code != "" && it.email != ""){
                    code = it.code
                    email = it.email
                }

            }
        }
    }
}