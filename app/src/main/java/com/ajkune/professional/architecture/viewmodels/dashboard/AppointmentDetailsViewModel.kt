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

    var settings = MutableLiveData<Settings>()
    var appointmentToken = MutableLiveData<AppointmentToken>()
    val allAppointments = SingleLiveData<List<AllAppointment>>()
    var successAppointment = SingleLiveData<Boolean>()
    var successAppointmentDashboard = SingleLiveData<Boolean>()
    var error = SingleLiveData<Exception>()
    var errorV2 = MutableLiveData<String>()


    fun getSettings(){
        dashboardRest.getSettings(){settings , exception ->
            if (settings != null){
                this.settings.postValue(settings)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

    fun getAppointmentToken(username : String, password : String){
        dashboardRest.getAppointmentToken(username, password){appointmentToken , exception ->
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

    fun getAllAppointmentByDate(appointmentToken : String, date : String, nextDate : String){
        dashboardRest.getAppointmentByDate(appointmentToken,date, nextDate){allAppointments, exception ->
            if (allAppointments !=null){
                this.allAppointments.postValue(allAppointments)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun addNewAppointmentInDashboard(startTime : String, title: String){
        dashboardRest.addNewAppointmentInDashboard(startTime,title){success, exception ->
            if (success){
                this.successAppointmentDashboard.postValue(success)
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