package fr.isen.fili.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.fili.androiderestaurant.basket.JsonItemBasket
import fr.isen.fili.androiderestaurant.databinding.BasketCellBinding

private lateinit var binding: BasketCellBinding

class BasketListAdapter(private val data: MutableList<JsonItemBasket>, private val deleteItemListener: (JsonItemBasket) -> Unit) : RecyclerView.Adapter<BasketListAdapter.BasketHolder>() {
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
        holder.tarif.text = (data.quantity * data.item.getJustPrice().toDouble()).toString()
        holder.number.text = data.quantity.toString()
        val picture = data.item.getFirstPicture()
        if(picture != null && picture.isNotEmpty()){
            Picasso.get()
                .load(picture)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(holder.image)
        }
        //Bouton delete
        holder.remove.setOnClickListener {
            //on delete l'item en dur
            deleteItem(position)
            //puis on delete l'item dans le fichier en passant par l'activité
            deleteItemListener.invoke(data)
        }
    }

    fun deleteItem(position: Int) {
        if(data[position].quantity != 1){
            data[position].quantity--
        } else {
            data.removeAt(position)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BasketHolder(binding: BasketCellBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.dishName
        val tarif = binding.totalPrice
        val image = binding.dishPicture
        val number = binding.quantity
        val remove = binding.removeItem
    }
}

