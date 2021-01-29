package fr.isen.fili.androiderestaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import fr.isen.fili.androiderestaurant.CarouselFragment.Companion.ARG_OBJECT
import fr.isen.fili.androiderestaurant.databinding.CarouselItemBinding

class FragmentAdapter (activity: AppCompatActivity, val list : List<String?>): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = CarouselFragment()
        fragment.arguments = Bundle().apply{
            putString(ARG_OBJECT, list[position])
        }
        return fragment
    }
}