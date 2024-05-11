package com.fredy.mytest.Api

sealed class ApiCredentials{
    object TabScanner{
        const val BASE_URL = "https://api.tabscanner.com/"
        const val API_KEY = "g4blivNJELeCBedDKeI4Ig1ImE63Y6ZbFZI78wSASxGhnI5hjer1HGnRQ5NulvQs"
        const val GET_CREDIT = "/api/credit"
        const val GET_RESULT = "/api/result"
        const val POST_RECEIPT = "/api/v2/process"
    }
}