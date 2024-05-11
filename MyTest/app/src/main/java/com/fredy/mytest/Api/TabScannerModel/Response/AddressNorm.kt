package com.fredy.mytest.Api.TabScannerModel.Response

data class AddressNorm(
    val building: String,
    val city: String,
    val country: String,
    val number: String,
    val postcode: String,
    val state: String,
    val street: String,
    val suburb: String
)