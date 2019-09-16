package com.kakaopay.kakaopaypretest.model

import com.kakaopay.kakaopaypretest.constant.KAKAO_REST_KEY
import com.kakaopay.kakaopaypretest.common.RetrofitProvider
import io.reactivex.Single
import java.net.URLEncoder

class MainRepository {

    fun searchImage(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int): Single<com.kakaopay.core.entity.SearchResultEntity> {
        val header = "KakaoAK $KAKAO_REST_KEY"
        return RetrofitProvider.getSearchService()
            .searchImage(header, URLEncoder.encode(query, "UTF-8"), sort.name, page, size)
    }


}