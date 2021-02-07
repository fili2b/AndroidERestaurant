package fr.isen.fili.androiderestaurant.basket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.*
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.APP_PREFS
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.BASKET_COUNT
import fr.isen.fili.androiderestaurant.RegisterActivity.Companion.ID_CLIENT
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
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)

        //Checker si client est connecté
        /*binding.payButton.setOnClickListener() {
            //Si le client n'est pas connecté alors on l'envoie sur la page de connexion, sinon sur order
            if (ID_CLIENT == "0") {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        }*/

        val file = File(cacheDir.absolutePath + "Basket.json")
        binding.payButton.setOnClickListener() {
            //On verifie si le panier est vide
            if (BASKET_COUNT == "0" || !file.exists()) {
                Snackbar.make(binding.root, "Votre panier est vide", Snackbar.LENGTH_LONG).show()
            } else {
                //s'il ne l'est pas, on procede au paiement ou a l'authentification
                if (sharedPreferences.contains(ID_CLIENT)) {
                    val intent = Intent(this, OrderActivity::class.java)
                    startActivityForResult(intent, 4)
                } else {
                    Snackbar.make(
                        binding.payButton,
                        "Veuillez vous connecter",
                        Snackbar.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivityForResult(intent, 4)
                }
            }
        }

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
            //foodRecycler.isVisible = true
        }
        invalidateOptionsMenu()
    }

    fun resetBasket(basket: JsonBasket) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "Basket.json")
        saveInMemory(basket, file)
    }

    private fun saveInMemory(basket: JsonBasket, file: File) {
        saveDishCount(basket)
        file.writeText(GsonBuilder().create().toJson(basket))
    }

    private fun saveDishCount(basket: JsonBasket) {
        val count = basket.items.sumOf { it.quantity }
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        sharedPreferences.edit().putInt(BASKET_COUNT, count).apply()
        //invalidateOptionsMenu()
    }

    /*companion object {
        const val APP_PREFS = "app_prefs"
        const val BASKET_COUNT = "basket_count"
    }*/
}


