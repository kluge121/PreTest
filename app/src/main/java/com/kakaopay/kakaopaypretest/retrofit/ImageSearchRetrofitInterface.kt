package com.kakaopay.kakaopaypretest.retrofit

import com.kakaopay.kakaopaypretest.model.SearchResult

import com.kakaopay.kakaopaypretest.constant.IMAGE_SEARCH_API_SUB_URL
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ImageSearchRetrofitInterface {
    @GET(IMAGE_SEARCH_API_SUB_URL)
    fun searchImage(
        @Header("Authorization") auth: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<SearchResult>
}