package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.Server
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuestionService {

    @GET(Server.url + "user/survey/questions/")
    suspend fun getSurveyQuestions()

    companion object {
        val instance: QuestionService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(QuestionService::class.java)
        }
    }
}