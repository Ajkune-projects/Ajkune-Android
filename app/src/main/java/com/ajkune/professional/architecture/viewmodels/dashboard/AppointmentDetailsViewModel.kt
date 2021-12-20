package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.AllAppointment
import com.ajkune.professional.architecture.models.AppointmentToken
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.models.UserById
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import javax.inject.Inject

class AppointmentDetailsViewModel @Inject constructor(val dashboardRest: DashboardRest): ViewModel() {

    var appointmentToken = MutableLiveData<AppointmentToken>()
    val allAppointments = SingleLiveData<List<AllAppointment>>()
    var error = SingleLiveData<Exception>()


    fun getAppointmentToken(){
        dashboardRest.getAppointmentToken(){appointmentToken , exception ->
            if (appointmentToken != null){
                this.appointmentToken.postValue(appointmentToken)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

    fun getAllAppointments(appointmentToken : String){
        dashboardRest.getAllAppointments(appointmentToken){allAppointments, exception ->
            if (allAppointments !=null){
                this.allAppointments.postValue(allAppointments)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

}