package fr.isen.fili.androiderestaurant

import com.google.gson.annotations.SerializedName
import fr.isen.fili.androiderestaurant.model.Dish
import java.io.Serializable

data class JsonBasket(
        @SerializedName("totalquantity") var totalquantity: Int,
        @SerializedName("basket")var basket: List<JsonItemBasket>): Serializable