package fr.isen.fili.androiderestaurant.basket

import com.google.gson.annotations.SerializedName
import fr.isen.fili.androiderestaurant.model.Dish
import java.io.Serializable

data class JsonItemBasket(
        @SerializedName("quantity") var quantity: Int,
        @SerializedName("item")var item: Dish) :Serializable