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



                allAppointment.forEach {

                    val splitStartDate = it.attributes?.startsAt?.split("T")
                    val startdate = splitStartDate?.get(0)

                    val time = splitStartDate?.get(1)
                    val startTime = time?.take(5)

                    val splitEndDate = it.attributes?.endsAt?.split("T")

                    val endDate = splitEndDate?.get(1)
                    val endTime = endDate?.take(5)

                    val finalTime = "$startTime - $endTime"

                    if (selectedDate == startdate){
                        if (finalTime == appointment.time){
                            binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                            binding.clMain.setBackgroundResource(0)
                        }else{
                            binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_a8466f))
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
                        }

                    }
                }


            if (selectedPosition != null) {
                if (selectedPosition == position) {
                    binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f)

                } else {
                    binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_a8466f))
                    binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
                }
            } else {
                binding.txtTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_a8466f))
                binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
            }

            binding.root.setOnClickListener {
                listener.onAppointmentClicked(appointment)
                selectedPosition =
                    if (selectedPosition == adapterPosition) adapterPosition else adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    interface Listener{
        fun onAppointmentClicked(appointment: Appointment)
    }
}
