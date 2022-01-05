package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.*
import com.ajkune.professional.networking.AddAppointmentRetrofit
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AppointmentDetailsViewModel @Inject constructor(val dashboardRest: DashboardRest): ViewModel() {

    var appointmentToken = MutableLiveData<AppointmentToken>()
    val allAppointments = SingleLiveData<List<AllAppointment>>()
    var successAppointment = SingleLiveData<Boolean>()
    var error = SingleLiveData<Exception>()
    var errorV2 = MutableLiveData<String>()


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

    fun getAllAppointmentByDate(appointmentToken : String, date : String){
        dashboardRest.getAppointmentByDate(appointmentToken,date){allAppointments, exception ->
            if (allAppointments !=null){
                this.allAppointments.postValue(allAppointments)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun addNewAppointment(appointmentToken : String, appointmentBody: AppointmentBody){
        dashboardRest.addNewAppointment(appointmentToken,appointmentBody){success, exception ->
            if (success){
                this.successAppointment.postValue(success)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun addNewAppointmentV2(appointmentToken : String, bodyV2: BodyV2){
        AddAppointmentRetrofit.service()
            .addNewAppointment("application/vnd.api+json","Bearer $appointmentToken", bodyV2)
            .enqueue(object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorV2.postValue(t.cause?.message)
                }

                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {

                    if (response.isSuccessful){
                        successAppointment.value = true
                    }else{
                        errorV2.postValue(response.errorBody()?.string())
                    }
                }
            })
    }

}