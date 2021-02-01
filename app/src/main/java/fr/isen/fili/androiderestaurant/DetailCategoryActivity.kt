package fr.isen.fili.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.basket.JsonBasket
import fr.isen.fili.androiderestaurant.basket.JsonItemBasket
import fr.isen.fili.androiderestaurant.carrousel.FragmentAdapter
import fr.isen.fili.androiderestaurant.databinding.ActivityDetailCategoryBinding
import fr.isen.fili.androiderestaurant.model.Dish
import java.io.File

private lateinit var binding: ActivityDetailCategoryBinding

class DetailCategoryActivity : AppCompatActivity() {

    private var quantity = 0
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_category)

        binding = ActivityDetailCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get preferences
        sharedPreferences = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

        //Afficher le titre
        val dish = intent.getSerializableExtra("dish") as Dish
        binding.dishTitle.text = dish.title

        //Afficher les ingrédients
        binding.dishIngredient.text = dish.ingredients.map{ it.name }.joinToString(", ")

        //Afficher le prix
        binding.dishPrice.text = dish.getPrice()

        //Carousel
        dish.getAllPictures()?.let {
            binding.Carousel.adapter = FragmentAdapter(this, it)
        }

        //Quantity selector plus
        binding.plusButton.setOnClickListener{
            if (quantity < 10){
                quantity += 1
            }
            binding.counter.text = quantity.toString()
            //On met à jour le prix total
            var totalprice = dish.getJustPrice().toFloat() * quantity
            binding.totalOrder.text = totalprice.toString() + "€"
        }

        //Quantity selector minus
        binding.minusButton.setOnClickListener{
            if(quantity > 0){
                quantity -= 1
            }
            binding.counter.text = quantity.toString()
            //On met à jour le prix total
            var totalprice = (dish.getJustPrice().toFloat() * quantity)
            binding.totalOrder.text = totalprice.toString() + "€"
        }

        //Gestion du panier
        binding.orderButton.setOnClickListener{
            Snackbar.make(binding.root, "Article ajouté au panier", Snackbar.LENGTH_LONG).show()
            addToBasket(dish, quantity)
        }
    }

    /*fun addToBasket(item: Dish, num : Int) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val article = JsonItemBasket(num, item)
        val file = File(cacheDir.absolutePath + "Basket.json")
        if (file.exists()){
            val json = gson.fromJson(file.readText(), JsonBasket::class.java)
            json.items += article
            file.writeText(Gson().toJson(json))
        } else {
            val jsonObject = Gson().toJson(JsonBasket(listOf(article)))
            file.writeText(jsonObject)
        }
    }*/

    fun addToBasket(item: Dish, quantity : Int) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "Basket.json")
        if (file.exists()){
            val basket = gson.fromJson(file.readText(), JsonBasket::class.java)
            basket.items.firstOrNull() { it.item == item }?.let {
                it.quantity += quantity
            } ?: run {
                basket.items.add(JsonItemBasket(quantity,item))
            }
            saveInMemory(basket, file)
        }else {
            val basket = JsonBasket(mutableListOf(JsonItemBasket(quantity, item)))
            saveInMemory(basket, file)
        }
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

    //Gestion du caddie
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.basket -> {
                startActivity(
                    Intent(applicationContext, BasketActivity::class.java)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}