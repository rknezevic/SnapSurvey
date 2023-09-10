package com.robertknezevic.qme

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Response
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.robertknezevic.qme.databinding.ActivitySignInBinding
import com.robertknezevic.qme.models.Server
import com.robertknezevic.qme.services.LoginService
import com.robertknezevic.qme.services.LoginServiceInterface
import com.robertknezevic.qme.services.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    private lateinit var loginService: LoginService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loginServiceInterface: LoginServiceInterface
    private lateinit var userService: UserService
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        loginServiceInterface = LoginServiceInterface.instance
        loginService = LoginService(sharedPreferences, loginServiceInterface)
        userService = UserService.instance



        val scope = CoroutineScope(Dispatchers.Main)


        binding.goToReg.setOnClickListener{
            val intent = Intent(this, SignUpActivity :: class.java)
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){ // robertknezevic@gmail.com
                scope.launch {
                    val response = loginService.loginAsync(email, password)
                    Log.d("RESPONSE IZ SIA", response.toString())
                        if(response){
                            val intent = Intent(this@SignInActivity, MainActivity :: class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this@SignInActivity, "Login failed!" , Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                Toast.makeText(this@SignInActivity, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}