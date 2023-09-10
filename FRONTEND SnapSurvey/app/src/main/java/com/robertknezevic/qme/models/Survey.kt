package com.robertknezevic.qme.models

data class Survey(
    val Id: String,
    val Title: String,
    val Description: String

) {
    companion object {
        fun fromResponseList(responseDataList: List<Map<String, Any>>): List<Survey> {
            return responseDataList.map { responseData ->
                val id = responseData["Id"] as String
                val title = responseData["Title"] as String
                val description = responseData["Description"] as String
                Survey(id, title, description)
            }
        }
    }
}