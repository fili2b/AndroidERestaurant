package fr.isen.fili.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.fili.androiderestaurant.RegisterActivity.Companion.ID_CLIENT
import fr.isen.fili.androiderestaurant.basket.BasketActivity
import fr.isen.fili.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.fili.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.fili.androiderestaurant.model.LoginJson
import fr.isen.fili.androiderestaurant.model.UserJson
import org.json.JSONObject
import java.io.File

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //swipe to register
        binding.arrow.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            /* val itemTouchHelper = adapter?.let { ArrowSwipe(it) }?.let { ItemTouchHelper(it) }
            if (itemTouchHelper != null) {
                itemTouchHelper.attachTo(OrderList)
            }*/
        }

        //send login form
        binding.btnLogin.setOnClickListener(){
            if(validForm()){
                sendLogin()
            }
        }
    }

    private fun validForm(): Boolean{
        val notBlank = binding.logPassword.text.isNotBlank() && binding.logEmail.text.isNotBlank()
        val goodEmail = !binding.logEmail.text.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(binding.logEmail.text).matches()
        if(!notBlank || !goodEmail){
            Snackbar.make(binding.root, "Veuillez remplir tous les champs", Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun sendLogin() {
        val request = UserJson("1", binding.logEmail.text.toString(),binding.logPassword.text.toString())
        val requestQueue = Volley.newRequestQueue(this)
        val postUrl = "http://test.api.catering.bluecodegames.com/user/login"
        val postData = JSONObject(Gson().toJson(request))
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData, { response ->
                if(response.toString().contains("data")) {
                    val gson = Gson().fromJson(response.toString(), LoginJson::class.java)
                    val sharedPreferences = getSharedPreferences(BasketActivity.APP_PREFS, MODE_PRIVATE)
                    sharedPreferences.edit().putString(ID_CLIENT, gson.data.id.toString()).apply()
                    Snackbar.make(binding.root, "Vous êtes connecté(e)", Snackbar.LENGTH_LONG).show()
                    redirectUser()
                } else{
                    Snackbar.make(binding.root, "Compte inexistant", Snackbar.LENGTH_LONG).show()
                }
            },
            {
                Snackbar.make(binding.root, "Erreur de connexion", Snackbar.LENGTH_LONG).show()
            }
        )
        requestQueue.add(stringRequest)
    }

    private fun redirectUser(){
        val file = File(cacheDir.absolutePath + "Basket.json")
        val request = Order("1", ID_CLIENT, file)
        val requestQueue = Volley.newRequestQueue(this)
        val postUrl = "http://test.api.catering.bluecodegames.com/user/order"
        val postData = JSONObject(Gson().toJson(request))
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData, { response ->
                if(response.toString().contains("data")) {
                    val gson = Gson().fromJson(response.toString(), LoginJson::class.java)
                    val sharedPreferences = getSharedPreferences(BasketActivity.APP_PREFS, MODE_PRIVATE)
                    sharedPreferences.edit().putString(ID_CLIENT, gson.data.id.toString()).apply()
                    Snackbar.make(binding.root, "Vous êtes connecté(e)", Snackbar.LENGTH_LONG).show()
                    redirectUser()
                } else{
                    Snackbar.make(binding.root, "Compte inexistant", Snackbar.LENGTH_LONG).show()
                }
            },
            {
                Snackbar.make(binding.root, "Erreur de connexion", Snackbar.LENGTH_LONG).show()
            }
        )
        requestQueue.add(stringRequest)
        val intent = Intent(this, BasketActivity::class.java)
        startActivity(intent)
    }
}