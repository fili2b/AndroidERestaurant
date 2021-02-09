package fr.isen.fili.androiderestaurant.basket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.*
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.APP_PREFS
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.BASKET_COUNT
import fr.isen.fili.androiderestaurant.RegisterActivity.Companion.ID_CLIENT
import fr.isen.fili.androiderestaurant.databinding.ActivityBasketBinding
import java.io.File

private lateinit var binding: ActivityBasketBinding

class BasketActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)

        //Checker si client est connecté
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
                //On verifie si la quantité vaut 0, si oui on supprime l'item du fichier
                if (it.quantity != 0) {
                    it.quantity
                } else {
                    basket.items.remove(it)
                }

                //On met a jour
                saveInMemory(basket, file)
            }
            foodRecycler.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun saveInMemory(basket: JsonBasket, file: File) {
        saveDishCount(basket)
        file.writeText(GsonBuilder().create().toJson(basket))
    }

    private fun saveDishCount(basket: JsonBasket) {
        val count = basket.items.sumOf { it.quantity }
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        sharedPreferences.edit().putInt(BASKET_COUNT, count).apply()
        invalidateOptionsMenu()
    }

    override fun onResume() {
        invalidateOptionsMenu()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(ACTIVITY, "destroyed")
    }

    companion object {
        const val ACTIVITY = "BasketActivity"
    }
}


