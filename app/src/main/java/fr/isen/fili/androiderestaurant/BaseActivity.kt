package fr.isen.fili.androiderestaurant

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.APP_PREFS
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.BASKET_COUNT
import fr.isen.fili.androiderestaurant.RegisterActivity.Companion.ID_CLIENT
import fr.isen.fili.androiderestaurant.basket.BasketActivity
import fr.isen.fili.androiderestaurant.databinding.ActivityOrderBinding

open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        //Gestion du caddie
        menuInflater.inflate(R.menu.menu_action_bar, menu);
        val menuBasket = menu?.findItem(R.id.basket)?.actionView
        val count = menuBasket?.findViewById<TextView>(R.id.basketCount)
        val sharedPrefrences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        count?.text = sharedPrefrences.getInt(BASKET_COUNT, 0).toString()

        val menuHome = menu?.findItem(R.id.home)?.actionView
        val menuLogout = menu?.findItem(R.id.logout)?.actionView

        menuBasket?.setOnClickListener {
            startActivity(Intent(this, BasketActivity::class.java))
        }

        //Gestion de l'icon home
        menuHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        //Gestion de l'icon logout
        menuLogout?.setOnClickListener {
            if (sharedPreferences.contains(ID_CLIENT)) {
                with(sharedPreferences.edit()) {
                    remove(ID_CLIENT)
                    apply()
                }
                Snackbar.make(menuLogout, "Vous êtes déconnecté(e)", Snackbar.LENGTH_LONG).show()
            }
            else {
                Snackbar.make(menuLogout, "Vous êtes déjà déconnecté(e)", Snackbar.LENGTH_LONG).show()
            }
        }

        return super.onCreateOptionsMenu(menu);
    }
}