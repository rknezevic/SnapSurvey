package com.robertknezevic.qme.models

data class Answer(
    val UserId: String,
    val QuestionId: String,
    val AnswerText: String,
    val SelectedOptionId: String?
    )
