package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.activities.DashboardActivity
import com.ajkune.professional.architecture.viewmodels.dashboard.AddAddressViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.AddAddressFragmentBinding
import javax.inject.Inject

class AddAddressFragment : BaseFragment() {

    lateinit var binding : AddAddressFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    var address : String = ""
    var street : String = ""
    var zipCode : Int = 0
    var country : String = ""

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
        binding.etStreet.setText(street)
        binding.etCity.setText(address)
        binding.etCountry.setText(country)
        binding.etZipCode.setText(zipCode.toString())
    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
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