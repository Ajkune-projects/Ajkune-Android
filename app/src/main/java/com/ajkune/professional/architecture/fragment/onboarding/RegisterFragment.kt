package com.ajkune.professional.architecture.fragment.onboarding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.onboarding.RegisterViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.RegisterFragmentBinding
import com.ajkune.professional.utilities.data.Constants
import com.ajkune.professional.utilities.extensions.isEmail
import javax.inject.Inject

class RegisterFragment : BaseFragment() {

    lateinit var binding : RegisterFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[RegisterViewModel::class.java]
        initBaseFunctions()
    }

    override fun onLoad() {
        viewModel.accountManager.observe(this, Observer {
            if (it != null) {
                hideLoader()
                if (it.error != null){
                    Toast.makeText(requireContext(), it.error!!.email?.get(0)!!, Toast.LENGTH_LONG).show()
                }
                if (it.data != null){
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    Constants.delay(2000){
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }

                    }

                }
            }
        })
    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            checkAllFieldsAndCallLoginService()
        }
    }

    override fun setToolbar() {
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = requireActivity().getColor(R.color.white)
   }

    private fun checkAllFieldsAndCallLoginService(){
        if (allLoginFieldsAreFilledCorrect()) {
            registerService()
        }
    }

    private fun allLoginFieldsAreFilledCorrect(): Boolean {
        var warningMessage = ""
        if (binding.etEmail.text.toString().isEmpty()) {
            warningMessage = "Please fill your email."
        } else if (!binding.etEmail.text.toString().isEmail) {
            warningMessage = "Please fill a valid email."
        } else if (binding.etFirstName.text.toString().isEmpty()) {
            warningMessage = "Please fill your first name."
        } else if (binding.etLastName.text.toString().isEmpty()) {
            warningMessage = "Please fill your last name."
        } else if (binding.etPassword.text.toString().isEmpty()) {
            warningMessage = "Please fill your password."
        } else if (binding.etConfirmPassword.text.toString().isEmpty()) {
            warningMessage = "Please fill your confrim password."
        } else if (binding.etConfirmPassword.text.toString() != binding.etPassword.text.toString()) {
            warningMessage = "Password don't match!"
        }

        if (!warningMessage.isEmpty()) {
            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerService(){
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.register(
            firstName,
            lastName,
            email,
            password,
        )
    }
}