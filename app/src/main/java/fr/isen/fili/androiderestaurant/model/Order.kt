package fr.isen.fili.androiderestaurant.model
import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.Serializable

data class Order (
    @SerializedName("id_shop") val id_shop : String,
    @SerializedName("id_user") val id_user : String,
    @SerializedName("basket") val basket : String
) : Serializable
