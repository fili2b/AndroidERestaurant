package fr.isen.fili.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val toast = Toast.makeText(applicationContext, "Entrées sélectionnées", Toast.LENGTH_SHORT)
            toast.show()
            startActivity(intent)
            finish()
        }
        binding.platButton.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            val toast = Toast.makeText(applicationContext, "Plats sélectionnés", Toast.LENGTH_SHORT)
            toast.show()
            startActivity(intent)
            finish()
        }
        binding.dessertButton.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            val toast = Toast.makeText(applicationContext, "Desserts sélectionnés", Toast.LENGTH_SHORT)
            toast.show()
            startActivity(intent)
            finish()
        }
    }


}