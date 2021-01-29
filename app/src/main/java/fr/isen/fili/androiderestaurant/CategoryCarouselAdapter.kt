package fr.isen.fili.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import fr.isen.fili.androiderestaurant.databinding.CarouselItemBinding

class CategoryCarouselAdapter(activity: AppCompatActivity, val list : List<String>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
/*
    private lateinit var binding : CarouselItemBinding
    private lateinit var viewPager: ViewPager2

    companion object{
        const val ARG_OBJECT = "object"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
         fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ):View? {
            binding = CarouselItemBinding.inflate(inflater, container, false)
            val view = binding.root
            return view
        }

         fun onViewCreated(view: View, savedInstanceState: Bundle?){
            arguments?.takeIf{
                it.containsKey(ARG_OBJECT)?.apply {
                    val img = arguments?.getString(ARG_OBJECT)

                    if ( img != null && img.isNotEmpty()){
                        Picasso.get()
                            .load(img)
                            .fit().centerCrop()
                            .placeholder(R.drawable.errorloading)
                            .error(R.drawable.errorloading)
                            .into(binding.imageCarousel)
                    }
                }
            }
        }
    }*/
}