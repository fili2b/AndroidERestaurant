package fr.isen.fili.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val title: String,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("images") private val pictures: List<String>,
    @SerializedName("prices") private val prices: List<Price>
): Serializable {
    fun getPrice() = prices[0].price + "€"
    fun getFormattedPrice() = prices[0].price + "€"
    fun getJustPrice() = prices[0].price
    fun getFirstPicture() = if (pictures.isNotEmpty() && pictures[0].isNotEmpty()) {
        pictures[0]
    } else {
        null
    }
    fun getAllPictures() = if(pictures.isNotEmpty() && pictures.any {it.isNotEmpty()}) {
        pictures.filter { it.isNotEmpty() }
    }else {
        null
    }
}