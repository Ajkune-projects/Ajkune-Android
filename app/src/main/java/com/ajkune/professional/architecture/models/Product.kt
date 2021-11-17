package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Product {

    var id : Int = 0
    var name : String = ""
    var price : String = ""
    var rating : Int = 0
    var image : String = ""
    var status : Int = 0


    @SerializedName("initial_price")
    var initialPrice : String = ""

    @SerializedName("wp_product_url")
    var wpProductUrl : String = ""

    companion object {
        fun create(string: String): Product? {
            return Gson().fromJson(string, Product::class.java)
        }

        fun createArray(string: String): List<Product>? {
            return Gson().fromJson<List<Product>>(
                string, object : TypeToken<List<Product>>() {}.type
            )
        }
    }

}