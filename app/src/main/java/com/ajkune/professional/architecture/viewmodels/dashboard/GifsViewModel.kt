package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.HasUserGiftsResponse
import com.ajkune.professional.architecture.models.LoginResponse
import com.ajkune.professional.architecture.models.VerifyUserAccountBody
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import javax.inject.Inject

class GifsViewModel @Inject constructor(val dashboardRest: DashboardRest) : ViewModel() {

    var hasUserGifts = MutableLiveData<HasUserGiftsResponse>()
    var error = SingleLiveData<Exception>()


    fun hasGifts(){
        dashboardRest.hasGifts{account , exception ->
            if (account != null){
                hasUserGifts.postValue(account)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

}