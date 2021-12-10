package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.architecture.models.UserById
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import javax.inject.Inject

class MyProfileViewModel @Inject constructor(val dashboardRest: DashboardRest): ViewModel() {

    var userById = MutableLiveData<UserById>()
    var error = SingleLiveData<Exception>()


    fun getUserById(){
        dashboardRest.getUserById(){userById , exception ->
            if (userById != null){
                this.userById.postValue(userById)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

}