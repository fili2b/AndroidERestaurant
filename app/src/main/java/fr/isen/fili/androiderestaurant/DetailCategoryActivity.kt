package fr.isen.fili.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.fili.androiderestaurant.carrousel.FragmentAdapter
import fr.isen.fili.androiderestaurant.databinding.ActivityDetailCategoryBinding
import fr.isen.fili.androiderestaurant.model.Dish
import java.io.File

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
            addToBasket(dish, quantity)
            displayMessage()
        }
    }

    fun addToBasket(item: Dish, num : Int) {
        val article = JsonItemBasket(num, item)
        val file = File(cacheDir.absolutePath + "Basket.json")
        if (file.exists()){
            val json = Gson().fromJson(file.readText(), JsonBasket::class.java)
            json.totalquantity += num
            json.basket += article
            file.writeText(Gson().toJson(json))
        } else {
            val jsonObject = Gson().toJson(JsonBasket(num, listOf(article)))
            file.writeText(jsonObject)
        }
    }

    // Alert window creation
    fun displayMessage() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Article ajouté au panier").setCancelable(true)
        val alert = dialogBuilder.create()
        alert.show()
    }
}