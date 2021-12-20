package com.ajkune.professional.architecture.fragment.dashboard

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.adapters.AppointmentAdapter
import com.ajkune.professional.architecture.adapters.ProductsAdapter
import com.ajkune.professional.architecture.models.AllAppointment
import com.ajkune.professional.architecture.models.Appointment
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.viewmodels.dashboard.AppointmentDetailsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.AppointmentDetailsFragmentBinding
import com.google.gson.Gson
import javax.inject.Inject

class AppointmentDetailsFragment : BaseFragment() , AppointmentAdapter.Listener{

    lateinit var binding : AppointmentDetailsFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    lateinit var appointmentAdapter : AppointmentAdapter

    var appointment : MutableList<Appointment> = mutableListOf()

    var appointmentToken : String = ""

    var allAppointment : MutableList<AllAppointment> = mutableListOf()

    var selectedDate : String = ""

    companion object {
        fun newInstance() = AppointmentDetailsFragment()
    }

    private lateinit var viewModel: AppointmentDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.appointment_details_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[AppointmentDetailsViewModel::class.java]
        getData()
        initBaseFunctions()
    }

    override fun onLoad() {
        appointment.add(Appointment("09:00 - 10:00"))
        appointment.add(Appointment("10:00 - 11:00"))
        appointment.add(Appointment("11:00 - 12:00"))
        appointment.add(Appointment("12:00 - 13:00"))
        appointment.add(Appointment("13:00 - 14:00"))
        appointment.add(Appointment("14:00 - 15:00"))
        appointment.add(Appointment("15:00 - 16:00"))
        appointment.add(Appointment("16:00 - 17:00"))
        appointment.add(Appointment("17:00 - 18:00"))


        showLoader()
        viewModel.getAppointmentToken()

        viewModel.appointmentToken.observe(this, Observer {
            if (it != null) {
                hideLoader()
                appointmentToken = it.accessToken
                viewModel.getAllAppointments(appointmentToken)
            }
        })

        viewModel.allAppointments.observe(this, Observer {
            if (it != null) {
                hideLoader()
                allAppointment.addAll(it)
                initRecyclerViewAppointment()


            }
        })
    }

    override fun onError() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClickEvents() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setToolbar() {
    }

    private fun initRecyclerViewAppointment(){
        val recyclerView = binding.rvAppointment
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        appointmentAdapter = AppointmentAdapter(this, allAppointment, selectedDate)
        appointmentAdapter.setData(appointment)
        appointmentAdapter.setHasStableIds(true)
        recyclerView.adapter = appointmentAdapter

    }

    private fun getData(){
        arguments?.let { bundle ->
            AppointmentDetailsFragmentArgs.fromBundle(bundle).let {
                selectedDate = it.date
            }
        }
    }

    override fun onAppointmentClicked(appointment: Appointment) {

    }

}