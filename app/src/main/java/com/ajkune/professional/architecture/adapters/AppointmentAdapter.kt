package com.ajkune.professional.architecture.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkune.professional.R
import com.ajkune.professional.architecture.models.AllAppointment
import com.ajkune.professional.architecture.models.Appointment
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemCategoryBinding
import com.ajkune.professional.databinding.ItemChooseAppointmentBinding

class AppointmentAdapter(val listener : Listener, var allAppointment : List<AllAppointment>,var  selectedDate : String) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(),
    BindableAdapter<List<Appointment>> {

    var items: List<Appointment> = listOf()

    var selectedPosition: Int? = null

    var isClickedRoot : Boolean = false


    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: List<Appointment>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemChooseAppointmentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_choose_appointment, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AppointmentAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder( val binding: ItemChooseAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(appointment: Appointment, position: Int) {
            binding.txtTime.text = appointment.time


            for (currentAppointment in allAppointment){
                val splitStartDate = currentAppointment.attributes?.startsAt?.split("T")
                val startdate = splitStartDate?.get(0)

                val time = splitStartDate?.get(1)
                val startTime = time?.take(5)

                val splitEndDate = currentAppointment.attributes?.endsAt?.split("T")

                val endDate = splitEndDate?.get(1)
                val endTime = endDate?.take(5)

                val finalTime = "$startTime - $endTime"

                    if (finalTime == appointment.time){
                        appointment.isAppointmentFree = false
                        binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_8c93a9))
                        binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        break
                    }else{
                        appointment.isAppointmentFree = true
                    }
            }


            if (isClickedRoot){
                if (selectedPosition != null) {
                    if (selectedPosition == position) {
                        if (appointment.isAppointmentFree){
                            binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f)
                        }
                    } else {
                        if (appointment.isAppointmentFree){
                            binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_a8466f))
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
                        }
                    }
                } else {
                    if (appointment.isAppointmentFree){
                        binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_a8466f))
                        binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
                    }
                }
            }


            binding.root.setOnClickListener {
                if (appointment.isAppointmentFree){
                    isClickedRoot = true
                    listener.onAppointmentClicked(appointment)
                    selectedPosition =
                        if (selectedPosition == adapterPosition) adapterPosition else adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }

    interface Listener{
        fun onAppointmentClicked(appointment: Appointment)
    }
}
