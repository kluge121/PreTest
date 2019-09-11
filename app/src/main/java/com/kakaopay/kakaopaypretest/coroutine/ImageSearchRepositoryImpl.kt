package com.kakaopay.kakaopaypretest.coroutine

import com.kakaopay.kakaopaypretest.constant.KAKAO_REST_KEY
import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.model.SearchResultEntity


class ImageSearchRepositoryImpl(
    private val remote: ImageSearchRetrofitInterface
) : ImageSearchRepository {

    override suspend fun getImageSearchResult(
        query: String,
        sort: KakaoImageSearchSortEnum,
        page: Int,
        size: Int
    ): SearchResultEntity {
        val header = "KakaoAK $KAKAO_REST_KEY"
        return remote.searchImage(header, query, sort.toString(), page, size)
    }


}
