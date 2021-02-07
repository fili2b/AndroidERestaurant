package fr.isen.fili.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.fili.androiderestaurant.DetailCategoryActivity.Companion.APP_PREFS
import fr.isen.fili.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.fili.androiderestaurant.model.LoginJson
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : BaseActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //send register form
        binding.btnRegister.setOnClickListener(){
            if(everythingValid()){
                sendAccount()
                Snackbar.make(binding.root, "Compte créée", Snackbar.LENGTH_LONG).show()
            }
        }

        //swipe to login
       /* binding.arrow.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/

        binding.arrow.setOnTouchListener(object : SwipeButton(applicationContext){
            override fun onSwipeRight() {
                changePage()
            }
        })
    }

    private fun changePage() {
        Toast.makeText(applicationContext, "Login page", Toast.LENGTH_SHORT).show();
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun sendAccount(){
        //Requete POST avec Volley
        val requestQueue = Volley.newRequestQueue(this)
        val postUrl = "http://test.api.catering.bluecodegames.com/user/register"

        //On remplit les champs avec les informations saisies
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("firstname", binding.firstName.text)
            postData.put("lastname", binding.lastName.text)
            postData.put("email", binding.email.text)
            postData.put("address", binding.address.text)
            postData.put("password", binding.password.text)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, postUrl, postData,
            {
                val gson = Gson().fromJson(it.toString(), LoginJson::class.java)
                val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
                sharedPreferences.edit().putString(ID_CLIENT, gson.data.id.toString()).apply()
            },
            {
                Log.i("state", "request failed")
            })
        requestQueue.add(jsonObjectRequest)
    }

    private fun everythingValid(): Boolean{
        val notBlank = binding.firstName.text.isNotBlank() && binding.lastName.text.isNotBlank() && binding.address.text.isNotBlank() && binding.email.text.isNotBlank() && binding.password.text.isNotBlank()
        //val goodPassword = binding.password.text.toString().length >= 8
        val goodEmail = !binding.email.text.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(
            binding.email.text
        ).matches()
        if(!notBlank || !goodEmail){
            Snackbar.make(
                binding.root,
                "Veuillez remplir tous les champs et saisir un email correct",
                Snackbar.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(ACTIVITY, "destroyed")
    }

    companion object {
        const val ID_CLIENT = "id_client"
        const val ACTIVITY = "RegisterActivity"
    }
}