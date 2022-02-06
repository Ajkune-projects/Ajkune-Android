package com.ajkune.professional.architecture.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkune.professional.R
import com.ajkune.professional.architecture.models.AllAppointment
import com.ajkune.professional.architecture.models.Appointment
import com.ajkune.professional.base.abstractactivity.BindableAdapter
import com.ajkune.professional.databinding.ItemChooseAppointmentBinding
import okhttp3.internal.Version
import java.util.*

class AppointmentAdapter(
    val listener: Listener,
    var allAppointment: List<AllAppointment>,
    var dayOfMonth: Int
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(),
    BindableAdapter<List<Appointment>> {

    var items: List<Appointment> = listOf()

    var selectedPosition: Int? = null

    var isClickedRoot: Boolean = false


    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: List<Appointment>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentAdapter.ViewHolder {
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


    inner class ViewHolder(val binding: ItemChooseAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(appointment: Appointment, position: Int) {
            binding.txtTime.text = appointment.time


            for (currentAppointment in allAppointment) {
                val splitStartDate = currentAppointment.attributes?.startsAt?.split("T")
                val startdate = splitStartDate?.get(0)

                val time = splitStartDate?.get(1)
                val startTime = time?.take(5)

                val splitEndDate = currentAppointment.attributes?.endsAt?.split("T")

                val endDate = splitEndDate?.get(1)
                val endTime = endDate?.take(5)

                val finalTime = "$startTime - $endTime"

                if (!appointment.checkAppointment) {
                    binding.txtTime.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cl_8c93a9
                        )
                    )
                    binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                }


                //Kjo pjes tregon nese dita ka perfundu dhe ka termine te lira duhet mi ba disable te pa klikushme, ka mbet me testu per terminet gjat orarit te dites
                val calendar: Calendar = Calendar.getInstance()
                val currentTime: Int = calendar.get(Calendar.HOUR_OF_DAY)
                val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                val timeToCheck = appointment.time.take(2)
                if (currentDayOfMonth == dayOfMonth) {
                    if (currentTime >= timeToCheck.toInt()) {
                        appointment.isAppointmentFree = false
                        binding.txtTime.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cl_8c93a9
                            )
                        )
                        binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                    }
                }
                ///////////////////////


                if (startTime == appointment.time.split(" ")[0]) {
                    if (currentAppointment.attributes!!.state != "canceled") {

                        if (currentAppointment.attributes!!.steps?.get(0)?.duration in 30..89) {
                            appointment.isAppointmentFree = false
                            appointment.checkAppointment = false
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_8c93a9
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        }


                        if (currentAppointment.attributes!!.steps?.get(0)?.duration in 90..149) {
                            appointment.isAppointmentFree = false
                            appointment.checkAppointment = false
                            items[position + 1].isAppointmentFree = false
                            items[position + 1].checkAppointment = false
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_8c93a9
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        }


                        if (currentAppointment.attributes!!.steps?.get(0)?.duration in 150..209) {
                            appointment.isAppointmentFree = false
                            items[position + 1].isAppointmentFree = false
                            items[position + 1].checkAppointment = false
                            items[position + 2].isAppointmentFree = false
                            items[position + 2].checkAppointment = false
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_8c93a9
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        }

                        if (currentAppointment.attributes!!.steps?.get(0)?.duration in 210..269) {
                            appointment.isAppointmentFree = false
                            items[position + 1].isAppointmentFree = false
                            items[position + 1].checkAppointment = false
                            items[position + 2].isAppointmentFree = false
                            items[position + 2].checkAppointment = false
                            items[position + 3].isAppointmentFree = false
                            items[position + 3].checkAppointment = false
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_8c93a9
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        }

                        if (currentAppointment.attributes!!.steps?.get(0)?.duration in 270..329) {
                            appointment.isAppointmentFree = false
                            items[position + 1].isAppointmentFree = false
                            items[position + 1].checkAppointment = false
                            items[position + 2].isAppointmentFree = false
                            items[position + 2].checkAppointment = false
                            items[position + 3].isAppointmentFree = false
                            items[position + 3].checkAppointment = false
                            items[position + 4].isAppointmentFree = false
                            items[position + 4].checkAppointment = false
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_8c93a9
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        }

                        if (currentAppointment.attributes!!.steps?.get(0)?.duration in 330..389) {
                            appointment.isAppointmentFree = false
                            items[position + 1].isAppointmentFree = false
                            items[position + 1].checkAppointment = false
                            items[position + 2].isAppointmentFree = false
                            items[position + 2].checkAppointment = false
                            items[position + 3].isAppointmentFree = false
                            items[position + 3].checkAppointment = false
                            items[position + 4].isAppointmentFree = false
                            items[position + 4].checkAppointment = false
                            items[position + 5].isAppointmentFree = false
                            items[position + 5].checkAppointment = false
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_8c93a9
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
                        }
                    }
                }
                    ////

//                if (startTime == appointment.time.split(" ")[0]) {
//                    when (currentAppointment.attributes!!.steps?.get(0)?.duration) {
//                        60 -> {
//                            appointment.isAppointmentFree = false
//                            appointment.checkAppointment = false
//                            binding.txtTime.setTextColor(
//                                ContextCompat.getColor(
//                                    binding.root.context,
//                                    R.color.cl_8c93a9
//                                )
//                            )
//                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
//                        }
//
//                        120 -> {
//                            appointment.isAppointmentFree = false
//                            items[position + 1].isAppointmentFree = false
//                            items[position + 1].checkAppointment = false
//                            binding.txtTime.setTextColor(
//                                ContextCompat.getColor(
//                                    binding.root.context,
//                                    R.color.cl_8c93a9
//                                )
//                            )
//                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
//                        }
//                        180 -> {
//                            appointment.isAppointmentFree = false
//                            items[position + 1].isAppointmentFree = false
//                            items[position + 1].checkAppointment = false
//                            items[position + 2].isAppointmentFree = false
//                            items[position + 2].checkAppointment = false
//                            binding.txtTime.setTextColor(
//                                ContextCompat.getColor(
//                                    binding.root.context,
//                                    R.color.cl_8c93a9
//                                )
//                            )
//                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
//
//                        } 240 ->{
//                        appointment.isAppointmentFree = false
//                        items[position + 1].isAppointmentFree = false
//                        items[position + 1].checkAppointment = false
//                        items[position + 2].isAppointmentFree = false
//                        items[position + 2].checkAppointment = false
//                        items[position + 3].isAppointmentFree = false
//                        items[position + 3].checkAppointment = false
//                        binding.txtTime.setTextColor(
//                            ContextCompat.getColor(
//                                binding.root.context,
//                                R.color.cl_8c93a9
//                            )
//                        )
//                        binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_opacity_a8466f_white)
//                        }
//                    }
//                }
                }


                if (isClickedRoot) {
                    if (selectedPosition != null) {
                        if (selectedPosition == position) {
                            if (appointment.isAppointmentFree) {
                                binding.txtTime.setTextColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.white
                                    )
                                )
                                binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f)
                            }
                        } else {
                            if (appointment.isAppointmentFree) {
                                binding.txtTime.setTextColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.cl_a8466f
                                    )
                                )
                                binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
                            }
                        }
                    } else {
                        if (appointment.isAppointmentFree) {
                            binding.txtTime.setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.cl_a8466f
                                )
                            )
                            binding.clMain.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f_white)
                        }
                    }
                }


            binding.root.setOnClickListener {
                if (appointment.isAppointmentFree) {
                    isClickedRoot = true
                    listener.onAppointmentClicked(appointment)
                    selectedPosition =
                        if (selectedPosition == adapterPosition) adapterPosition else adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }

    interface Listener {
        fun onAppointmentClicked(appointment: Appointment)
    }

    fun test(){
        var i = 0
        if (i in 1..99){

        }
    }
}
