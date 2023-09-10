package com.robertknezevic.qme.services
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.robertknezevic.qme.models.LoginModel
import com.google.firebase.auth.FirebaseAuth
import com.robertknezevic.qme.models.UserToken
import retrofit2.Response
import java.util.*

class LoginService(
    private val sharedPreferences: SharedPreferences,
    private val loginServiceInterface: LoginServiceInterface

    ) {

    @SuppressLint("CommitPrefEdits", "SuspiciousIndentation")

    suspend fun loginAsync(email: String, password: String): Boolean {

        try {
        val response: Response<LoginModel> = loginServiceInterface.loginAsync(email, password)
        response.errorBody()

        if (response.isSuccessful) {
            Log.d("response.body", response.body().toString()) // robertknezevic77@gmail.com lozinka
            val loginModel = response.body()

            if(loginModel != null){
                val userId = response.body()?.userId
                val token = response.body()?.customToken

                val editor = sharedPreferences.edit()
                editor.putString("user_token", token)
                editor.putString("user_id", userId)
                editor.apply()

                return true
            }
            return false

        }
       Log.d("OBOJE JE NULL", response.body().toString())
        return false
           } catch (e: Exception) {
               Log.d("saaaa", "kurcina")

              return false
    }
}

    suspend fun signoutAsync(): Boolean {
        try {
                val response: Response<Unit> = loginServiceInterface.signoutAsync()
                if(response.isSuccessful){
                    val editor = sharedPreferences.edit()
                    editor.remove("user_token")
                    editor.remove("user_id")
                    editor.apply()

                    return true

            }
            return false
        } catch (e: Exception) {
            return false
        }
    }

   /* fun isUserTokenValid(): Boolean {
        val userToken = getUserToken()
        return userToken != null && userToken.expires > Date()
    }*/

    /*fun getUserToken(): LoginModel? {
        val userTokenJson = sharedPreferences.getString("user_token", null)
        return userTokenJson?.let { LoginModel }
    }*/
}
