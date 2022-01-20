package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.adapters.AppointmentAdapter
import com.ajkune.professional.architecture.adapters.ProductsAdapter
import com.ajkune.professional.architecture.dialog.AppointmentMessageDialog
import com.ajkune.professional.architecture.models.*
import com.ajkune.professional.architecture.viewmodels.dashboard.AppointmentDetailsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.AppointmentDetailsFragmentBinding
import com.ajkune.professional.utilities.data.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
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
    var appointmentTime : String = ""

    var currentDayOfMonth : Int = 0

    private var dialog: AppointmentMessageDialog? = null

    var customerId : String = ""
    var merchantId : String = ""

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
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
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
        viewModel.getSettings()

        viewModel.settings.observe(this, Observer {
            if (it != null) {
                customerId = it.customerId
                merchantId = it.merchantId
                viewModel.getAppointmentToken(it.username,it.password)
            }
        })

        viewModel.appointmentToken.observe(this, Observer {
            if (it != null) {
                appointmentToken = it.accessToken
                val formattedDate = selectedDate+"T00:00:00.000"
                viewModel.getAllAppointmentByDate(appointmentToken,formattedDate)
            }
        })

        viewModel.allAppointments.observe(this, Observer {
            if (it != null) {
                hideLoader()
                allAppointment.addAll(it)
                initRecyclerViewAppointment()


            }
        })

        viewModel.successAppointment.observe(this, Observer {
            if (it != null) {
                hideLoader()
                dialog?.dismiss()
                findNavController().navigate(AppointmentDetailsFragmentDirections.actionAppointmentDetailsFragmentToSuccessAppointmentFragment(selectedDate, appointmentTime))
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

        binding.cvConfirmYourAppointment.setOnClickListener {
            appointmentMessageDialog()
        }
    }

    override fun setToolbar() {
    }

    override fun onDestroy() {
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.VISIBLE
        super.onDestroy()
    }

    private fun initRecyclerViewAppointment(){
        val recyclerView = binding.rvAppointment
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        appointmentAdapter = AppointmentAdapter(this, allAppointment, currentDayOfMonth)
        appointmentAdapter.setData(appointment)
        appointmentAdapter.setHasStableIds(true)
        recyclerView.adapter = appointmentAdapter

    }

    private fun getData(){
        arguments?.let { bundle ->
            AppointmentDetailsFragmentArgs.fromBundle(bundle).let {
                selectedDate = it.date
                currentDayOfMonth = it.dayOfMonth
            }
        }
    }

    override fun onAppointmentClicked(appointment: Appointment) {
        binding.cvConfirmYourAppointment.visibility = View.VISIBLE
        val appointmentStartTime = appointment.time.split(" ")
        appointmentTime = appointmentStartTime[0]

        //first show dialog to write message than make appointment
    }

    private fun appointmentMessageDialog() {
        this.let {
            dialog = AppointmentMessageDialog(
                requireContext(), object : AppointmentMessageDialog.Listener {
                    override fun onConfirmAppointmentClicked(message: String) {
                        val appointmentBody = AppointmentBody()
                        appointmentBody.type = "appointments"
                        val formattedDate = selectedDate+"T"+appointmentTime+":00.000+01:00"
                        appointmentBody.attributes.startsAt = formattedDate
                        appointmentBody.attributes.title = message
                        appointmentBody.attributes.participantCount = "1"
                        val steps = Steps()
                        steps.withCustomer = true
                        steps.duration = 60
                        appointmentBody.attributes.steps.add(steps)
                        appointmentBody.relationships.merchant.data.type = "merchants"
                        //kena me zavendsu me dinamike
                        appointmentBody.relationships.merchant.data.id = "2e4e05ba-3c28-48f9-a69a-51161ed44d80"

                        val bodyV2 = BodyV2()
                        bodyV2.data = appointmentBody

                        viewModel.addNewAppointmentV2(appointmentToken, bodyV2)
                        viewModel.addNewAppointmentInDashboard(formattedDate, message)
                    }
                }
            )
            val attrs = dialog?.window?.attributes
            attrs?.gravity = Gravity.CENTER_VERTICAL
            attrs?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND

            dialog?.window?.attributes = attrs
            dialog?.setCancelable(true)
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.show()
            dialog?.window?.setBackgroundDrawableResource(R.color.cl_transparent)
        }
    }

}