package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.LoginModel
import com.robertknezevic.qme.models.Server
import com.robertknezevic.qme.models.UserToken
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface LoginServiceInterface {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginAsync(@Field("email") email: String,
                           @Field("password") password: String): Response<LoginModel>

    @POST("auth/signout")
    suspend fun signoutAsync(): Response<Unit>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun registerAsync(@Field("firstName") firstName : String,
                              @Field("lastName") lastName : String,
                              @Field("username") username : String,
                              @Field("email") email: String,
                              @Field("password") password: String) :Response<Unit>

companion object {
        val instance: LoginServiceInterface by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(LoginServiceInterface::class.java)
        }
    }
}