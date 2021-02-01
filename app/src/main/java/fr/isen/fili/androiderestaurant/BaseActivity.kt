package fr.isen.fili.androiderestaurant

import android.content.Intent
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.fili.androiderestaurant.BasketActivity.Companion.APP_PREFS
import fr.isen.fili.androiderestaurant.BasketActivity.Companion.BASKET_COUNT

open class BaseActivity: AppCompatActivity() {
    //Gestion du caddie
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu);
        val menuView = menu?.findItem(R.id.basket)?.actionView
        val count = menuView?.findViewById<TextView>(R.id.basketCount)
        val sharedPrefrences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        count?.text = sharedPrefrences.getInt(BASKET_COUNT, 0).toString()

        menuView?.setOnClickListener{
            startActivity(Intent(this, BasketActivity::class.java))
        }
        return super.onCreateOptionsMenu(menu);
    }
}