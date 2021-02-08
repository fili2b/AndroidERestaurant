package fr.isen.fili.androiderestaurant


import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.APP_PREFS
import fr.isen.fili.androiderestaurant.RegisterActivity.Companion.ID_CLIENT
import fr.isen.fili.androiderestaurant.basket.JsonBasket
import fr.isen.fili.androiderestaurant.databinding.ActivityOrderBinding
import org.json.JSONObject
import java.io.File

private lateinit var binding: ActivityOrderBinding

class OrderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.orderLoader.isVisible = true
        binding.orderSuccess.isVisible = false
        binding.orderFail.isVisible = false

        sendBasket()
    }

    private fun sendBasket() {
        val requestQueue = Volley.newRequestQueue(this)
        val postUrl = "http://test.api.catering.bluecodegames.com/user/order"

        //On récupère le panier
        val file = File(cacheDir.absolutePath + "Basket.json")
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        val json: JsonBasket = gson.fromJson(file.readText(), JsonBasket::class.java)

        //On récupère l'id du client
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        val user = sharedPreferences.getString(ID_CLIENT, "0")

        //On met toutes les infos dans la requete
        val postData = JSONObject()
        postData.put("id_shop", "1")
        postData.put("id_user", user)
        postData.put("msg", json.toString())

        val stringRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData, { response ->
                Snackbar.make(binding.root, "Votre commande a bien été envoyée", Snackbar.LENGTH_LONG).show()

                //On affiche un loader
                binding.orderLoader.isVisible = false
                binding.orderSuccess.isVisible = true
                //On delete le fichier du panier
                file.delete()

                //On met a jour le menu
                invalidateOptionsMenu()

                //On met à 0 le nombre d'items dans le panier
                val sharedPreferences = getSharedPreferences(DetailCategoryActivity.APP_PREFS, MODE_PRIVATE)
                sharedPreferences.edit().putInt(DetailCategoryActivity.BASKET_COUNT, 0).apply()
            },
            {
                Snackbar.make(binding.root, "Echec de l'envoi", Snackbar.LENGTH_LONG).show()
                binding.orderLoader.isVisible = false
                binding.orderFail.isVisible = true
            }
        )
        requestQueue.add(stringRequest)
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

    companion object{
        const val ACTIVITY = "OrderActivity"
    }
}