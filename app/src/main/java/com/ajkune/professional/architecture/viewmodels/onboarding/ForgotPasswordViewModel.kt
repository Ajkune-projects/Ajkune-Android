package com.ajkune.professional.architecture.viewmodels.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.DefaultResponse
import com.ajkune.professional.rest.UserRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(val userRest: UserRest): ViewModel() {

    var  successSendEmail = MutableLiveData<DefaultResponse>()
    var error = SingleLiveData<Exception>()
    var successPswCode = MutableLiveData<DefaultResponse>()

    fun forgotPassword(email : String){
        userRest.forgotPassword(email){success, exception ->
            if (success != null){
                successSendEmail.postValue(success)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

    fun checkResetPasswordCode(finalCode: String) {
        userRest.checkResetPasswordCode(finalCode) { success, exception ->
            if (success != null){
                successPswCode.postValue(success)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }



}