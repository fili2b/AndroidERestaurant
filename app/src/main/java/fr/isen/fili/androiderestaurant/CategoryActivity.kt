package fr.isen.fili.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.isen.fili.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.fili.androiderestaurant.model.Dish
import fr.isen.fili.androiderestaurant.model.FoodDataJson
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding: ActivityCategoryBinding


class CategoryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryTitle = intent.getStringExtra(HomeActivity.CATEGORY)

        loadData(categoryTitle?:"")

        binding.categoryTitle.text = categoryTitle
        val joke = binding.blagueImage
        if (categoryTitle == "Entrées") {
            Picasso.get()
                .load(R.drawable.blaguewok)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(joke)
        }
        if (categoryTitle == "Plats"){
            Picasso.get()
                .load(R.drawable.poischiche)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(joke)
        }
        if (categoryTitle == "Desserts"){
            Picasso.get()
                .load(R.drawable.glace)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(joke)
        }
    }

        private fun loadData(category: String){
            //Requete POST avec Volley
            val postUrl = "http://test.api.catering.bluecodegames.com/menu"
            val requestQueue = Volley.newRequestQueue(this)
            val postData = JSONObject()
            try {
                postData.put("id_shop", "1")
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, postUrl, postData,
                {
                    val gson = Gson().fromJson(it.toString(), FoodDataJson::class.java)
                    gson.data.firstOrNull(){ it.name == category}?.dishes?.let{
                        dishes -> displayCategories(dishes)
                    }?: run { Log.e("CategoryActivity", "Pas de categorie trouvée")}
                },
                {error -> error.printStackTrace()})//afficher erreur au lieu loader

            requestQueue.add(jsonObjectRequest)
        }

        private fun displayCategories(menu: List<Dish>) {
            binding.categorieLoader.isVisible = false
            binding.listCategory.isVisible = true

            binding.listCategory.layoutManager = LinearLayoutManager(this)
            binding.listCategory.adapter = CategoryListAdapter(menu) {
                val intent = Intent(this, DetailCategoryActivity::class.java)
                intent.putExtra("dish", it)
                startActivity(intent)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(ACTIVITY, "destroyed")
    }

    companion object{
        const val ACTIVITY = "CategoryActivity"
    }
}