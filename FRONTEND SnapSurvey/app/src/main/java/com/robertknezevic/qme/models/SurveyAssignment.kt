package com.robertknezevic.qme.models

import java.util.*

data class SurveyAssignment(
    val Id: String,
    val SurveyId: String,
    val UserId: String,
    val UserGroupId: String,
    val IsFilled: Boolean,
    val DateFilled: Date = Date()
)
