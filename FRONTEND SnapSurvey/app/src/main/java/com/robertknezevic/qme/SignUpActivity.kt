package com.robertknezevic.qme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.robertknezevic.qme.databinding.ActivitySignUpBinding
import com.robertknezevic.qme.services.LoginService
import com.robertknezevic.qme.services.RegistrationService
import com.robertknezevic.qme.services.RegistrationServiceInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var registrationService : RegistrationService
    private lateinit var registrationServiceInterface: RegistrationServiceInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        registrationServiceInterface = RegistrationServiceInterface.instance

        registrationService = RegistrationService(registrationServiceInterface)
        val scope = CoroutineScope(Dispatchers.Main)


        binding.goToSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity :: class.java)
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener {
            val firstName = binding.regFirstName.text.toString()
            val lastName = binding.regLastName.text.toString()
            val username = binding.regUsername.text.toString()
            val email = binding.regEmail.text.toString()
            val password = binding.regPassword.text.toString()
            val confirmPass = binding.confirmPassword.text.toString()

            if(firstName.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()){
                if(password == confirmPass){
                    scope.launch {
                        val response = registrationService.registerUser(firstName, lastName, username, email, password)
                        if(response){
                            val intent = Intent(this@SignUpActivity, SignInActivity :: class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this@SignUpActivity, "Registration failed!" , Toast.LENGTH_SHORT).show()

                        }
                    }
                }else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}