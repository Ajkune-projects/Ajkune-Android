package com.ajkune.professional.architecture.viewmodels.onboarding

import androidx.lifecycle.ViewModel
import com.ajkune.professional.rest.UserRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(val userRest: UserRest): ViewModel() {

    var successChangedPsw = SingleLiveData<Boolean>()
    var error = SingleLiveData<Exception>()

    fun resetPassword(email: String, newPassword: String, confirmNewPassword : String ,resetPasswordCode : String) {
        userRest.resetPassword(email, newPassword, confirmNewPassword,resetPasswordCode) { success, exception ->
            if (success) {
                this.successChangedPsw.postValue(success)
            }
            if (exception != null) {
                error.postValue(exception)
            }
        }
    }

}