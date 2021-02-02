package fr.isen.fili.androiderestaurant.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.BaseActivity
import fr.isen.fili.androiderestaurant.basket.JsonBasket
import fr.isen.fili.androiderestaurant.basket.JsonItemBasket
import fr.isen.fili.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.fili.androiderestaurant.model.Dish
import java.io.File
import java.io.FileNotFoundException

private lateinit var binding: ActivityBasketBinding

class BasketActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readFile()
    }

    fun readFile() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "Basket.json")
        if (file.exists()) {
            val basket = gson.fromJson(file.readText(), JsonBasket::class.java)
            val foodRecycler = binding.basketRecyclerView
            foodRecycler.adapter = BasketListAdapter(basket.items.toMutableList()) {
                //On remove l'item dans le fichier
                basket.items.remove(it)
                //On met a jour
                resetBasket(basket)
            }
            foodRecycler.layoutManager = LinearLayoutManager(this)
            foodRecycler.isVisible = true
        }
    }
    fun resetBasket(basket: JsonBasket) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "Basket.json")
        saveInMemory(basket, file)
    }

    private fun saveInMemory(basket: JsonBasket, file: File){
        saveDishCount(basket)
        file.writeText(GsonBuilder().create().toJson(basket))
    }

    private fun saveDishCount(basket: JsonBasket){
        val count = basket.items.sumOf { it.quantity }
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        sharedPreferences.edit().putInt(BASKET_COUNT, count).apply()
    }

    companion object {
        const val APP_PREFS = "app_prefs"
        const val BASKET_COUNT = "basket_count"
    }
}


