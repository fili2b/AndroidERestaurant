package fr.isen.fili.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.fili.androiderestaurant.databinding.ActivityCategoryBinding

private lateinit var binding: ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryTitle.text = intent.getStringExtra(HomeActivity.CATEGORY)

    }
}