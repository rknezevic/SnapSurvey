package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.AnswerOption
import com.robertknezevic.qme.models.Server
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface AnswerOptionService {

    @GET(Server.url + "answerOption/{answerOptionId}")
    suspend fun getAnswerOption(@Path("answerOptionId") answerOptionId: String) : AnswerOption

    companion object {
        val instance: AnswerOptionService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(AnswerOptionService::class.java)
        }
    }
}