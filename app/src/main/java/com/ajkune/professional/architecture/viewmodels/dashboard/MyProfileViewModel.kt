package com.ajkune.professional.architecture.viewmodels.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.architecture.models.UserById
import com.ajkune.professional.architecture.models.VerifyUserAccountBody
import com.ajkune.professional.networking.AddAppointmentRetrofit
import com.ajkune.professional.networking.VerifyUserAccountRetrofit
import com.ajkune.professional.rest.DashboardRest
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.ajkune.professional.utilities.livedata.SingleLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MyProfileViewModel @Inject constructor(val dashboardRest: DashboardRest, val baseAccountManager: BaseAccountManager): ViewModel() {

    var userById = MutableLiveData<UserById>()
    var test = SingleLiveData<Boolean>()
    var error = SingleLiveData<Exception>()
    var errorV2 = MutableLiveData<String>()

    var successVerifyProfile = SingleLiveData<Boolean>()

    fun getUserById(){
        dashboardRest.getUserById(){userById , exception ->
            if (userById != null){
                this.userById.postValue(userById)
            }
            if (exception != null){
                error.postValue(exception)
            }
        }
    }

    fun verifyUserProfileV2(verifyUserAccountBody : VerifyUserAccountBody){
        dashboardRest.verifyUserProfileV2(verifyUserAccountBody){success , exception ->
            if (success){
                this.successVerifyProfile.postValue(success)
            }
            if (exception !=null){
                error.postValue(exception)
            }
        }
    }

//     fun verifyUserProfile(verifyUserAccountBody : VerifyUserAccountBody){
//        val token = baseAccountManager.token
//        VerifyUserAccountRetrofit.service()
//            .verifyUserAccountRetrofit(token!!, verifyUserAccountBody)
//            .enqueue(object : Callback<Boolean> {
//                override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                    errorV2.postValue(t.cause?.message)
//                }
//
//                override fun onResponse(
//                    call: Call<Boolean>,
//                    response: Response<Boolean>
//                ) {
//                    if (response.isSuccessful){
//                        successVerifyProfile.value = true
//                    }else{
//                        errorV2.postValue(response.errorBody()?.string())
//                    }
//                }
//            })
//    }



}