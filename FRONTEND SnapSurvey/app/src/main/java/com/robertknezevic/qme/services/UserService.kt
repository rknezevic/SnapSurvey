package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.Server
import com.robertknezevic.qme.models.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UserService {

    @GET(Server.url + "users/")
    suspend fun getAllSurveys() : List<User>

    companion object {
        val instance: UserService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(UserService::class.java)
        }
    }
}