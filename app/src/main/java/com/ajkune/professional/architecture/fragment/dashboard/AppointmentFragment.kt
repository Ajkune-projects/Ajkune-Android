
package com.ajkune.professional.architecture.fragment.dashboard

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R

import com.ajkune.professional.architecture.viewmodels.dashboard.AppointmentViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.databinding.AppointmentFragmentBinding
import kotlinx.android.synthetic.main.appointment_fragment.*


import android.widget.Toast

import android.widget.CalendarView.OnDateChangeListener
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*


class AppointmentFragment :  BaseFragment() {

    lateinit var calendarView: CalendarView

    lateinit var binding : AppointmentFragmentBinding

    companion object {
        fun newInstance() = AppointmentFragment()
    }

    private lateinit var viewModel: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.appointment_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        initBaseFunctions()
    }

    override fun onLoad() {
        calendarView = binding.calendarView// get the reference of CalendarView

        //calendarView.minDate = calendarView.date


    }

    override fun onError() {
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClickEvents() {
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.i("testCalendar", "$year $month $dayOfMonth")
            val updatedMonth = month+1

            var formattedMonth = ""
            formattedMonth = if (updatedMonth.toString().length ==1){
                "0$updatedMonth"
            }else{
                "$updatedMonth"
            }

            var formattedDayOfMonth = ""
            formattedDayOfMonth = if (dayOfMonth.toString().length == 1){
                "0$dayOfMonth"
            }else{
                "$dayOfMonth"
            }

            val date = "$year-$formattedMonth-$formattedDayOfMonth"
            findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToAppointmentDetailsFragment(date))
        }
    }

    override fun setToolbar() {

    }

}