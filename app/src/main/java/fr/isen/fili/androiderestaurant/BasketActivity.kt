package fr.isen.fili.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.basket.JsonBasket
import fr.isen.fili.androiderestaurant.basket.JsonItemBasket
import fr.isen.fili.androiderestaurant.databinding.ActivityBasketBinding
import java.io.File
import java.io.FileNotFoundException

private lateinit var binding: ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readFile()
    }

    fun readFile(){
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "Basket.json")
        if (file.exists()){
            val basket = gson.fromJson(file.readText(), JsonBasket::class.java)
            val foodRecycler = binding.basketRecyclerView
            foodRecycler.adapter = BasketListAdapter(basket.items.toMutableList(),applicationContext)
            foodRecycler.layoutManager = LinearLayoutManager(this)
            foodRecycler.isVisible = true
        }
    }
}

