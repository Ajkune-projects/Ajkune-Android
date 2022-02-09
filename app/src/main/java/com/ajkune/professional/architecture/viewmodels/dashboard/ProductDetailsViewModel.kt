package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.Comment
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.models.UserById
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(val dashboardRest: DashboardRest) : ViewModel() {

    var newComment = SingleLiveData<List<Product>>()
    var error = SingleLiveData<Exception>()

    val products = SingleLiveData<List<Product>>()


    fun addCommentForProduct(productId : Int, title : String, comment : String){
        dashboardRest.addCommentForProduct(productId, title, comment){userById , exception ->
            if (userById != null){
                this.newComment.postValue(userById)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

    fun addCommentForOffer(productId : Int, title : String, comment : String){
        dashboardRest.addCommentForOffer(productId, title, comment){userById , exception ->
            if (userById != null){
                this.newComment.postValue(userById)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

    fun getProductById(productId : Int){
        dashboardRest.getProductById(productId){products, exception ->
            if (products !=null){
                this.products.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun getOffersById(offerId : Int){
        dashboardRest.getOfferById(offerId){products, exception ->
            if (products !=null){
                this.products.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

}