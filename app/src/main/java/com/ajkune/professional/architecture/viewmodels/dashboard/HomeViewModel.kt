package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val dashboardRest: DashboardRest) : ViewModel() {


    val categories = SingleLiveData<List<Category>>()
    val products = SingleLiveData<List<Product>>()
    val productsByCategoryId = SingleLiveData<List<Product>>()
    var error = MutableLiveData<Exception>()

    fun getActiveCategories(){
        dashboardRest.getAllActiveCategories(){categories, exception ->
            if (categories !=null){
                this.categories.postValue(categories)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun getActiveProducts(){
        dashboardRest.getActiveProducts(){products, exception ->
            if (products !=null){
                this.products.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

    fun getProductsByCategoryId(categoryId: Int){
        dashboardRest.getProductsByCategoryId(categoryId){products, exception ->
            if (products !=null){
                this.productsByCategoryId.postValue(products)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

}