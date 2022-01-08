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
import com.ajkune.professional.architecture.viewmodels.dashboard.SuccessAppointmentViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.SuccessAppointmentFragmentBinding
import javax.inject.Inject

class SuccessAppointmentFragment : BaseFragment() {

    var appointmentDate : String = ""
    var appointmentTime : String = ""

    lateinit var binding : SuccessAppointmentFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = SuccessAppointmentFragment()
    }

    private lateinit var viewModel: SuccessAppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.success_appointment_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SuccessAppointmentViewModel::class.java]
        getData()
        initBaseFunctions()
    }

    override fun onLoad() {
            binding.txtAppointmentSuccessDateAndTime.text = getString(R.string.appointment_success_date_time,appointmentDate, appointmentTime)
    }

    override fun onError() {

    }

    override fun onClickEvents() {
            binding.btnBack.setOnClickListener {
                findNavController().popBackStack(R.id.appointmentFragment, false)
            }
    }

    override fun setToolbar() {

    }

    private fun getData() {
        arguments?.let { bundle ->
            SuccessAppointmentFragmentArgs.fromBundle(bundle).let {
                if (it.date != "" && it.time != "") {
                    appointmentDate = it.date
                    appointmentTime = it.time
                }
            }
        }
    }
}