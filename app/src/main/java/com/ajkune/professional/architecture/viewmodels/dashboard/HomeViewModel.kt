package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.livedata.SingleLiveData
import java.lang.Exception
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val dashboardRest: DashboardRest) : ViewModel() {


    val categories = SingleLiveData<List<Category>>()
    var error = MutableLiveData<Exception>()

    fun getActiveCategories(){
        dashboardRest.getAllActiveCategories(){kidClasses, exception ->
            if (kidClasses !=null){
                this.categories.postValue(kidClasses)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

}