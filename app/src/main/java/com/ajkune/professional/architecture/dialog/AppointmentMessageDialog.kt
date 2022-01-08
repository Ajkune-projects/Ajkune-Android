package com.ajkune.professional.architecture.dialog

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R
import com.ajkune.professional.databinding.DialogAppointmentMessageBinding

class AppointmentMessageDialog (var mcontext: Context, val listener : Listener)
    : Dialog(mcontext){


    lateinit var binding : DialogAppointmentMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_appointment_message, null, false)
        setContentView(binding.root)
        val params: WindowManager.LayoutParams = window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        window!!.attributes = params


        binding.btnConfirmAppointment.setOnClickListener {
            if (binding.etMessageOfAppointment.text.isNullOrBlank()){
                Toast.makeText(mcontext, "Please write a message", Toast.LENGTH_LONG).show()
            }else{
                listener.onConfirmAppointmentClicked(binding.etMessageOfAppointment.text.toString())
            }

        }
        binding.imgClose.setOnClickListener {
            dismiss()
        }



    }

    interface Listener{
        fun onConfirmAppointmentClicked(message : String)
    }

}