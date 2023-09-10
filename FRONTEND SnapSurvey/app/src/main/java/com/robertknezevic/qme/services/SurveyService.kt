package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.Survey
import com.robertknezevic.qme.models.Server
import com.robertknezevic.qme.models.UserToken
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface SurveyService {
    @GET(Server.url + "user/surveys")
    suspend fun getSurveys(@Header("Authorization")token: String): List<Survey>
    @GET(Server.url + "user/filledSurveys")
    suspend fun getFilledSurveys(@Header("Authorization") token: String): List<Survey>
    @GET(Server.url + "user/surveys/{surveyId}")
    suspend fun getSurveyById(
        @Path("surveyId") surveyId: String
        ): Survey
    companion object {
        val instance: SurveyService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(SurveyService::class.java)
        }
    }
}