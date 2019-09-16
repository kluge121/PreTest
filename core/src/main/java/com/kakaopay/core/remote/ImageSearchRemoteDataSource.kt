package com.kakaopay.core.remote

import com.kakaopay.core.model.search.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ImageSearchRemoteDataSource {


    @GET("/v2/search/image")
    suspend fun requestImageSearch(
        @Header("Authorization") auth: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : ImageSearchResponse
}