package fr.isen.fili.androiderestaurant.basket

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class JsonBasket(
        @SerializedName("items")var items: MutableList<JsonItemBasket>): Serializable