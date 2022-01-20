package com.ajkune.professional.architecture.fragment.onboarding

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.activities.DashboardActivity
import com.ajkune.professional.architecture.viewmodels.onboarding.LoginViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.LoginFragmentBinding
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    lateinit var binding : LoginFragmentBinding

    @Inject
    lateinit var viewModelFactory : AjkuneViewModelFactory

    @Inject
    lateinit var baseAccountManager : BaseAccountManager

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

        viewModel.accountManager.observe(this, Observer {
            if (it != null) {
                baseAccountManager.user = it
                baseAccountManager.user!!.token = it.token
                baseAccountManager.create(it, it.token)
                hideLoader()
                val intent = Intent(requireContext(), DashboardActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

    }

    override fun onError() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClickEvents() {

        binding.btnLogin.setOnClickListener {
            checkAllFieldsAndCallLoginService()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.txtForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
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
                showLoader()
                loginService()
            }
        }

    private fun loginService(){
        viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    private fun allLoginFieldsAreFilledCorrect(): Boolean {
        var warningMessage = ""
        if (binding.etEmail.text.toString().isEmpty()) {
            warningMessage = "Please fill required fields."
        } else if (binding.etPassword.text.toString().isEmpty()) {
            warningMessage = "Please fill required fields."
        }

        if (!warningMessage.isEmpty()) {
            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
}

}