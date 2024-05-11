package com.fredy.mysavings.Feature.Data.APIs.CurrencyModels.Response

data class ProcessResponse(
    val status: String,
    val token: String?,
    val message: String?
)
