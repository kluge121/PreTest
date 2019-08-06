package com.kakaopay.kakaopaypretest.model

import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.retrofit.ImageSearchRetrofitInterface
import com.kakaopay.kakaopaypretest.constant.KAKAO_API_BASE_URL
import com.kakaopay.kakaopaypretest.constant.KAKAO_REST_KEY
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

class MainRepository {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(KAKAO_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private val service: ImageSearchRetrofitInterface by lazy {
        retrofit.create(ImageSearchRetrofitInterface::class.java)
    }

    fun searchImage(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int): Single<SearchResult> {
        val header = "KakaoAK $KAKAO_REST_KEY"
        return service.searchImage(header, URLEncoder.encode(query, "UTF-8"), sort.name, page, size)
    }


}