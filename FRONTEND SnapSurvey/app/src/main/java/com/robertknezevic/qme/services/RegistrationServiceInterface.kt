package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.Server
import com.robertknezevic.qme.models.User
import com.robertknezevic.qme.models.UserRegisterModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationServiceInterface {
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun registerAsync(@Field("firstName") firstName : String,
                              @Field("lastName") lastName : String,
                              @Field("username") username : String,
                              @Field("email") email: String,
                              @Field("password") password: String) :Response<Unit>
    companion object {
        val instance: RegistrationServiceInterface by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(RegistrationServiceInterface::class.java)
        }
    }
}