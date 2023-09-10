package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.Answer
import com.robertknezevic.qme.models.Server
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface AnswerService {

    @POST(Server.url + "user/survey/questions/submit")
    suspend fun submitAnswer(@Body answers : List<Answer>)

    @GET(Server.url + "user/survey/questions/answers")
    suspend fun getAnwers(@Body surveyId : String) : List<Answer>

    companion object {
        val instance: AnswerService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(AnswerService::class.java)
        }
    }

}