package fr.isen.fili.androiderestaurant
import android.view.LayoutInflater
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import fr.isen.fili.androiderestaurant.databinding.CategoryCellBinding

class CategoryListAdapter(val categories: List<String>, private val categoriesClickListener: (String) -> Unit) : RecyclerView.Adapter<CategoryListAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryHolder {
        val itemBinding = CategoryCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.title.text = categories[position]
        holder.layout.setOnClickListener{
            categoriesClickListener.invoke(categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryHolder(binding: CategoryCellBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.dishName
        val layout = binding.root
    }

    data class Choix(val nom: String, val image: Int)
}

