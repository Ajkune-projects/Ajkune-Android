package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.architecture.models.Offer
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class OffersViewModel @Inject constructor(val dashboardRest: DashboardRest) : ViewModel() {

    val offers = SingleLiveData<List<Offer>>()
    val banners = SingleLiveData<List<Offer>>()
    val filterProductOrOffers = SingleLiveData<List<Offer>>()
    var error = MutableLiveData<Exception>()


    fun getAllOffers(){
        dashboardRest.getAllOffers(){products, exception ->
            if (products !=null){
                this.offers.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun filterOffers(minPrice : Int, maxPrice : Int, type : String){
        dashboardRest.filterOffers(minPrice,maxPrice,type){products, exception ->
            if (products !=null){
                this.filterProductOrOffers.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }


    fun getBanner(){
        dashboardRest.getBanner(){products, exception ->
            if (products !=null){
                this.banners.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

}