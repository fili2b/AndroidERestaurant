package fr.isen.fili.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.fili.androiderestaurant.databinding.ActivityCategoryBinding
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding: ActivityCategoryBinding
private lateinit var linearLayoutManager: LinearLayoutManager


class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryTitle.text = intent.getStringExtra(HomeActivity.CATEGORY)

        binding.listCategory.layoutManager = LinearLayoutManager(this)
        val menu = resources.getStringArray(R.array.choices_array).toList()

        //On rend la carte clickable et on lance l'activité détails au click
        binding.listCategory.adapter = CategoryListAdapter(menu){
            val intent = Intent(this, DetailCategoryActivity::class.java)
            intent.putExtra("category", it)
            startActivity(intent)
        }

        //Requete POST avec Volley
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            { response -> println(response) },
            {error -> error.printStackTrace()}
        )
        requestQueue.add(jsonObjectRequest)
    }


}