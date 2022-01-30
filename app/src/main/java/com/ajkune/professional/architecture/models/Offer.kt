package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Offer {

    var id : Int = 0
    var name : String = ""
    var price : String = ""
    var rating : Int = 0
    var image : String = ""
    var status : Int = 0

    //for banner
    var title : String = ""
    @SerializedName("image_path")
    var imagePath : String = ""

    @SerializedName("desc_en")
    var descEn : String = ""

    @SerializedName("initial_price")
    var initialPrice : String = ""

    @SerializedName("wp_product_url")
    var wpProductUrl : String = ""

    @SerializedName("comments_offer")
    var commentsOffer : List<Comment>? = null

    companion object {
        fun create(string: String): Offer? {
            return Gson().fromJson(string, Offer::class.java)
        }

        fun createArray(string: String): List<Offer>? {
            return Gson().fromJson<List<Offer>>(
                string, object : TypeToken<List<Offer>>() {}.type
            )
        }
    }
}