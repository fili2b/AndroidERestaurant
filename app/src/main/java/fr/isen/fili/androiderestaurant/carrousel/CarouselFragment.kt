package fr.isen.fili.androiderestaurant.carrousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.fili.androiderestaurant.R
import fr.isen.fili.androiderestaurant.databinding.CarouselItemBinding

private lateinit var binding: CarouselItemBinding

class CarouselFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CarouselItemBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getString(ARG_OBJECT)?.let {
            Picasso.get()
                .load(it)
                .fit().centerCrop()
                .placeholder(R.drawable.errorloading)
                .error(R.drawable.errorloading)
                .into(binding.imageCarousel)
        }
    }

    companion object {
        const val ARG_OBJECT = "object"
    }
}