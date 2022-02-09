package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.Gift
import com.ajkune.professional.architecture.models.GiftAddedResponse
import com.ajkune.professional.architecture.models.Offer
import com.ajkune.professional.architecture.models.VerifyUserAccountBody
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class LuckyWheelViewModel @Inject constructor(val dashboardRest: DashboardRest) : ViewModel() {

    val gifts = SingleLiveData<List<Gift>>()
    var error = MutableLiveData<Exception>()
    var gift = MutableLiveData<GiftAddedResponse>()

    fun getListOfGifts(){
        dashboardRest.getListOfGifts(){products, exception ->
            if (products !=null){
                this.gifts.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun addGiftFromSpinner(giftId : Int){
        dashboardRest.addGiftFromSpinner(giftId){gift , exception ->
            if (gift != null){
                this.gift.postValue(gift)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

}