package com.robertknezevic.qme.services

import com.robertknezevic.qme.models.Question
import com.robertknezevic.qme.models.Server
import com.robertknezevic.qme.models.Survey
import com.robertknezevic.qme.models.SurveyAssignment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SurveyAssignmentService {
    @GET(Server.url + "surveyAssignment/{surveyId}/{userId}")
    suspend fun getSurveyAssignment(@Path("surveyId") surveyId : String, @Path("userId") userId : String): SurveyAssignment

    @PUT(Server.url + "surveyAssignment/{surveyAssignmentId}")
    suspend fun updateSurveyAssignment(@Path("surveyAssignmentId") surveyAssignmentId: String)

    @GET(Server.url + "surveyAssignment/{surveyAssignmentId}/questions")
    suspend fun getSurveyQuestions(@Path("surveyAssignmentId") surveyAssignmentId: String): List<Question>

    companion object {
        val instance: SurveyAssignmentService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.url)
                .build()

            retrofit.create(SurveyAssignmentService::class.java)
        }
    }
}