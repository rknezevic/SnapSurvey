package com.robertknezevic.qme.models

import com.google.gson.Gson
import java.util.*

data class UserToken(
    val id: String,
    val token: String,
    val fullName: String,
    val email: String,
    val expires: Date = Date(), // UNIX timestamp
) {
    fun toJsonString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
    companion object {
        fun fromJsonString(json: String): UserToken {
            val gson = Gson()
            return gson.fromJson(json, UserToken::class.java)
        }

        fun fromResponse(responseData: Map<String, Any>): UserToken {
            val token = responseData["token"] as String
            val id = responseData["id"] as String
            val fullName = responseData["fullName"] as String
            val email = responseData["email"] as String
            val expires = responseData["expires"] as Date
            return UserToken(id, token, fullName, email, expires)
        }
    }
}
