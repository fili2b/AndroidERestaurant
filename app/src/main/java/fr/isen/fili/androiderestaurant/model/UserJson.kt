package fr.isen.fili.androiderestaurant.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserJson (
    @SerializedName("id_shop") val id_shop : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String
    ) : Serializable
