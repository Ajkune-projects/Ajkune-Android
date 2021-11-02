package com.ajkune.professional.utilities.data

import android.content.res.Resources
import java.util.*
import kotlin.concurrent.timerTask

object Constants {

    //dev url
    val baseUrl : String = "https://sciencescope-api.azurewebsites.net/api/"

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun delay(miliSec: Long, completion: () -> Unit) {
        Timer().schedule(timerTask {
            completion()
        }, miliSec)
    }

}