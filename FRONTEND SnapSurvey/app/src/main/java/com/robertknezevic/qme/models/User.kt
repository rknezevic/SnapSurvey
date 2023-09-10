package com.robertknezevic.qme.models
import java.util.Date

data class User(
    val Id: String,
    val FirstName: String,
    val LastName: String,
    val Username: String,
    val Email: String,
    val Password: String,
    val LoginTime: Date = Date(),
    val LogoutTime: Date = Date()
)