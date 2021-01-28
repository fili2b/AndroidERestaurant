package fr.isen.fili.androiderestaurant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.isen.fili.androiderestaurant.databinding.ActivityDetailCategoryBinding
import model.Dish
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding: ActivityDetailCategoryBinding

class DetailCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_category)

        binding = ActivityDetailCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Afficher le titre
        val dish = intent.getSerializableExtra("dish") as? Dish
        binding.dishTitle.text = dish?.title

        //Afficher les ingrédients
        binding.dishIngredient.text = dish?.ingredients?.map{ it.name }?.joinToString(", ")

        //Afficher le prix
        binding.dishPrice.text = dish?.getPrice()

        //Afficher l'image du plat
        //Afficher photo
        val image = dish?.getFirstPicture()
        if ( image != null && image.isNotEmpty()){
            Picasso.get()
                .load(image)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(binding.dishImage)
        }
    }
}