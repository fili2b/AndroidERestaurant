package fr.isen.fili.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import fr.isen.fili.androiderestaurant.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entreeButton.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            //val toast = Toast.makeText(applicationContext, "Entrées sélectionnées", Toast.LENGTH_SHORT)
            //toast.show()
            intent.putExtra(CATEGORY, getString(R.string.entree_title))
            startActivity(intent)
        }
        binding.platButton.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            //val toast = Toast.makeText(applicationContext, "Plats sélectionnés", Toast.LENGTH_SHORT)
            //toast.show()
            intent.putExtra(CATEGORY, getString(R.string.plat_title))
            startActivity(intent)
        }
        binding.dessertButton.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            //val toast = Toast.makeText(applicationContext, "Desserts sélectionnés", Toast.LENGTH_SHORT)
            //toast.show()
            intent.putExtra(CATEGORY, getString(R.string.dessert_title))
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY, "destroyed")
    }

    companion object{
        const val ACTIVITY = "HomeActivity"
        const val CATEGORY = "category"
    }
}