package com.fredy.mytest.Api.TabScannerModel.Response

data class ResultResponse(
    val code: Int,
    val message: String,
    val result: Result,
    val status: String,
    val status_code: Int,
    val success: Boolean
)