package com.kakaopay.kakaopaypretest.model

import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.retrofit.ImageSearchRetrofitInterface
import com.kakaopay.kakaopaypretest.constant.KAKAO_API_BASE_URL
import com.kakaopay.kakaopaypretest.constant.KAKAO_REST_KEY
import com.kakaopay.kakaopaypretest.retrofit.RetrofitProvider
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

class DetailRepository {


    fun searchDetailImage(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int): Single<SearchResult> {
        val header = "KakaoAK $KAKAO_REST_KEY"
        return RetrofitProvider.getSearchService().searchImage(header, URLEncoder.encode(query, "UTF-8"), sort.name, page + 1, size + 1)
    }


}