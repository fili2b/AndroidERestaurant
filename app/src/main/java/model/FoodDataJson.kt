package model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodDataJson (
    @SerializedName("data") val data: List<Category>
) : Serializable