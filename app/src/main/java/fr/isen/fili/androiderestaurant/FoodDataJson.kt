package fr.isen.fili.androiderestaurant

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodDataJson (
    @SerializedName
        (value: "name_Fr") val name: String
) : Serializable