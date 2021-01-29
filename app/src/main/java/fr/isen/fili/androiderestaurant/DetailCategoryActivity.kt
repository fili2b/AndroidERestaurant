package fr.isen.fili.androiderestaurant

import android.content.Intent
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

    private var quantity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_category)

        binding = ActivityDetailCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Afficher le titre
        val dish = intent.getSerializableExtra("dish") as Dish
        binding.dishTitle.text = dish.title

        //Afficher les ingrédients
        binding.dishIngredient.text = dish.ingredients.map{ it.name }.joinToString(", ")

        //Afficher le prix
        binding.dishPrice.text = dish.getPrice()

        //Afficher une image du plat
        /*val image = dish.getFirstPicture()
        if ( image != null && image.isNotEmpty()){
            Picasso.get()
                .load(image)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(binding.dishImage)
        }*/

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

    }
}