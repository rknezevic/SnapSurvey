package com.robertknezevic.qme.models

object HttpHeader{
    fun getAuthorizationHeader(token: String): Map<String, String> {
        return mapOf("Authorization" to "Bearer $token")
    }
}