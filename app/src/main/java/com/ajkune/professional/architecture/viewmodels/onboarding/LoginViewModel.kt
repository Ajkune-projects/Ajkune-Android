package com.ajkune.professional.architecture.viewmodels.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.di.anotation.ForUserRest
import com.ajkune.professional.rest.UserRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import javax.inject.Inject

class LoginViewModel @Inject constructor(val userRest: UserRest): ViewModel() {

    var accountManager = MutableLiveData<User>()
    var error = SingleLiveData<Exception>()


    fun login(email : String, password : String){
        userRest.login(email, password){account , exception ->
            if (account != null){
                accountManager.postValue(account)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

}