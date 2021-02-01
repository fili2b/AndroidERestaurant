package fr.isen.fili.androiderestaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.fili.androiderestaurant.basket.JsonItemBasket
import fr.isen.fili.androiderestaurant.databinding.BasketCellBinding

private lateinit var binding: BasketCellBinding

class BasketListAdapter(private val data: MutableList<JsonItemBasket>, applicationContext: Context) : RecyclerView.Adapter<BasketListAdapter.BasketHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasketHolder {
        binding = BasketCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BasketHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        val data : JsonItemBasket = data[position]
        holder.title.text = data.item.title
        holder.tarif.text = data.item.getFormattedPrice()
        val picture = data.item.getFirstPicture()
        if(picture != null && picture.isNotEmpty()){
            Picasso.get()
                .load(picture)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BasketHolder(binding: BasketCellBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.dishName
        val layout = binding.root
        val tarif = binding.totalPrice
        val image = binding.dishPicture
    }
}

