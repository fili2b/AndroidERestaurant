package model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val title: String,
    //@SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("images") private val pictures: List<String>
    //@SerializedName("prices") private val prices: List<Price>
): Serializable {
    //fun getPrice()
}