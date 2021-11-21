package com.ajkune.professional.architecture.viewmodels.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.DefaultResponse
import com.ajkune.professional.rest.UserRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class RegisterViewModel @Inject constructor(val userRest: UserRest): ViewModel() {

    var accountManager = MutableLiveData<DefaultResponse>()
    var error = SingleLiveData<Exception>()

    fun register(firstName : String, lastName: String, email: String, password: String){
        userRest.register(firstName, lastName, email, password){account, exception ->
            if (account != null){
                accountManager.postValue(account)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }
}