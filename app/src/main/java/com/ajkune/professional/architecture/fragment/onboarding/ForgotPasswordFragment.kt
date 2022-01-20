package com.ajkune.professional.architecture.fragment.onboarding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.onboarding.ForgotPasswordViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.ForgotPassowordFragmentBinding
import com.ajkune.professional.utilities.extensions.isEmail
import javax.inject.Inject

class ForgotPasswordFragment : BaseFragment() {

    lateinit var binding : ForgotPassowordFragmentBinding

    private var first: String = ""
    private var second: String = ""
    private var thrird: String = ""
    private var fourth: String = ""
    private var fifth: String = ""
    private var sixth: String = ""

    var finalCode: String = ""
    var email: String = ""

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.forgot_passoword_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ForgotPasswordViewModel::class.java]
        initBaseFunctions()
    }

    override fun onLoad() {

        viewModel.successSendEmail.observe(this, Observer {
            if (it != null) {
                if (!it.success){
                    Toast.makeText(requireContext(), getString(R.string.email_not_found), Toast.LENGTH_LONG).show()
                }else{
                    hideLoader()
                    binding.clEmail.visibility = View.GONE
                    binding.constraintLayoutVerification.visibility = View.VISIBLE
                }

            }
        })

        viewModel.successPswCode.observe(this, Observer {
            if (it != null) {
                hideLoader()
                if(it.success){
                    findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToResetPasswordFragment(finalCode, email))
                }else{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.firstDigit.setText("")
                    binding.secondDigit.setText("")
                    binding.thirdDigit.setText("")
                    binding.forthDigit.setText("")
                    binding.fifthDigit.setText("")
                    finalCode = ""
                    binding.firstDigit.requestFocus()
                }

            }
        })
    }

    override fun onError() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(requireContext(), getString(R.string.email_not_found), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClickEvents() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnReset.setOnClickListener {
            if (!binding.etEmail.text.isNullOrBlank()){
                if (!binding.etEmail.text.toString().isEmail){
                    Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_LONG).show()
                }else{
                    email = binding.etEmail.text.toString()
                    viewModel.forgotPassword(email)
                    showLoader()
                }

            }
            else{
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_LONG).show()
            }
        }


        binding.firstDigit.doOnTextChanged { text, start, count, after ->
            if (text?.length == 1) {
                first = text.toString()
                binding.secondDigit.requestFocus()
            }
        }
        binding.secondDigit.doOnTextChanged { text, start, count, after ->
            if (text?.length == 1) {
                second = text.toString()
                binding.thirdDigit.requestFocus()
            }
        }
        binding.thirdDigit.doOnTextChanged { text, start, count, after ->
            if (text?.length == 1) {
                thrird = text.toString()
                binding.forthDigit.requestFocus()
            }
        }
        binding.forthDigit.doOnTextChanged { text, start, count, after ->
            if (text?.length == 1) {
                fourth = text.toString()
                binding.fifthDigit.requestFocus()

            }
        }
        binding.fifthDigit.doOnTextChanged { text, start, count, after ->
            if (text?.length == 1) {
                showLoader()
                fifth = text.toString()
                finalCode = "$first$second$thrird$fourth$fifth"
                viewModel.checkResetPasswordCode(finalCode)

            }
        }


        binding.secondDigit.setOnKeyListener(View.OnKeyListener { _, keyCode, event -> // You can identify which key pressed buy checking keyCode value
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                binding.firstDigit.setText("")
                binding.firstDigit.requestFocus()
            }
            false
        })

        binding.thirdDigit.setOnKeyListener(View.OnKeyListener { _, keyCode, event -> // You can identify which key pressed buy checking keyCode value
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                binding.secondDigit.setText("")
                binding.secondDigit.requestFocus()
            }
            false
        })

        binding.forthDigit.setOnKeyListener(View.OnKeyListener { _, keyCode, event -> // You can identify which key pressed buy checking keyCode value
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                binding.thirdDigit.setText("")
                binding.thirdDigit.requestFocus()
            }
            false
        })

        binding.fifthDigit.setOnKeyListener(View.OnKeyListener { _, keyCode, event -> // You can identify which key pressed buy checking keyCode value
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                binding.forthDigit.setText("")
                binding.forthDigit.requestFocus()
            }
            false
        })
    }

    override fun setToolbar() {
    }
}