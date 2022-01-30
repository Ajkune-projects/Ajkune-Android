package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.activities.DashboardActivity
import com.ajkune.professional.architecture.models.Address
import com.ajkune.professional.architecture.viewmodels.dashboard.AddAddressViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.AddAddressFragmentBinding
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.google.gson.Gson
import javax.inject.Inject

class AddAddressFragment : BaseFragment() {

    lateinit var binding : AddAddressFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    var address : String = ""
    var street : String = ""
    var zipCode : Int = 0
    var country : String = ""

    @Inject
    lateinit var baseAccountManager : BaseAccountManager

    companion object {
        fun newInstance() = AddAddressFragment()
    }

    private lateinit var viewModel: AddAddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_address_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[AddAddressViewModel::class.java]
        getData()
        initBaseFunctions()
    }


    override fun onLoad() {

        if (address != ""){
            binding.txtAddressTitle.text = getString(R.string.edit_your_address)
            binding.etStreet.setText(street)
            binding.etCity.setText(address)
            binding.etCountry.setText(country)
            binding.etZipCode.setText(zipCode.toString())
        }else{
            binding.txtAddressTitle.text = getString(R.string.add_new_address)
        }

    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            if (validateFields()){
                val currentStreet = binding.etStreet.text.toString()
                val currentCity = binding.etCity.text.toString()
                val currentZipCode = binding.etZipCode.text.toString()
                val currentCountry = binding.etCountry.text.toString()
                val currentAddress = Address(currentStreet, currentCity, currentZipCode, currentCountry)
                val jsonCurrentAddress = Gson().toJson(currentAddress)
                baseAccountManager.currentUserAddress = jsonCurrentAddress
                requireActivity().onBackPressed()
            }
        }
    }

    private fun validateFields() : Boolean {
        var warningMessage = ""
        if (binding.etStreet.text.toString().isBlank()) {
            warningMessage = "Please fill all requirements Fields"
        } else if (binding.etCity.text.toString().isBlank()) {
            warningMessage = "Please fill all requirements Fields"
        }
        else if (binding.etZipCode.text.toString().isBlank()) {
            warningMessage = "Please fill all requirements Fields"
        }
        else if (binding.etCountry.text.toString().isBlank()){
            warningMessage = "Please fill all requirements Fields"
        }
        if (warningMessage.isNotEmpty()) {
            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun setToolbar() {
    }

    private fun getData() {
        arguments?.let { bundle ->
            AddAddressFragmentArgs.fromBundle(bundle).let {
                if (it.address != ""){
                    address = it.address
                    street = it.street
                    zipCode = it.zipcode
                    country = it.country
                }

            }
        }
    }

}