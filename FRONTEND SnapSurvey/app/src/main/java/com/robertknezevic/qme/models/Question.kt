package com.robertknezevic.qme.models

data class Question(
    val Id: String,
    val SurveyId: String,
    val QuestionText: String,
    val AnswerOptionIds: List<String>,
    val IsAnswered: Boolean = false,
    val ImageUrl: String
) {

}