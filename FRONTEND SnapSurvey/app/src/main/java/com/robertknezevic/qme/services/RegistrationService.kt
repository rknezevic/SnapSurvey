package com.robertknezevic.qme.services

import android.util.Log
import com.robertknezevic.qme.models.User
import com.robertknezevic.qme.models.UserRegisterModel
import retrofit2.Response

class RegistrationService(private val registrationServiceInterface: RegistrationServiceInterface) {
    suspend fun registerUser(firstName: String, lastName: String, username: String, email: String, password: String): Boolean {
        try {
            val response: Response<Unit> = registrationServiceInterface.registerAsync(firstName, lastName, username, email, password)
            if (response.isSuccessful) {
                return true
            }
        } catch (e: Exception) {
            throw e
        }
        return false
    }
}