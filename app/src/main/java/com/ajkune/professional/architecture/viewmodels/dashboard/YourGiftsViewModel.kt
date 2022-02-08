package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.Gift
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class YourGiftsViewModel @Inject constructor(val dashboardRest: DashboardRest): ViewModel() {

    val gifts = SingleLiveData<List<Gift>>()
    var error = MutableLiveData<Exception>()

    fun getListOfGifts(){
        dashboardRest.getUserGifts(){products, exception ->
            if (products !=null){
                this.gifts.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }
}