package com.berdimyradov.myapplication.data.remote

import com.berdimyradov.myapplication.domain.model.CalorieResponse
import com.berdimyradov.myapplication.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CalorieApi {


    @Headers(value = ["X-Api-key: $API_KEY"])
    @GET("/v1/nutrition")
    suspend fun searchForProduct(
        @Query("query") query: String
    ): CalorieResponse
}