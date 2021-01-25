package fr.isen.fili.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.fili.androiderestaurant.databinding.ActivityCategoryBinding

private lateinit var binding: ActivityCategoryBinding
private lateinit var linearLayoutManager: LinearLayoutManager


class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryTitle.text = intent.getStringExtra(HomeActivity.CATEGORY)

        binding.listCategory.layoutManager = LinearLayoutManager(this)
        //val userName = ressources.geyStringArray(R.array.planets_array).toList()
        binding.listCategory.adapter = CategoryListAdapter(listOf("julien","pierre","paul"))
    }
}