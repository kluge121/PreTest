package com.kakaopay.kakaopaypretest.coroutine

import com.kakaopay.kakaopaypretest.constant.DEFAULT_SEARCH_IMAGE_PAGE
import com.kakaopay.kakaopaypretest.constant.DEFAULT_SEARCH_IMAGE_SIZE
import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.model.SearchResultEntity


interface ImageSearchRepository {

    suspend fun getImageSearchResult(
        query: String,
        sort: KakaoImageSearchSortEnum = KakaoImageSearchSortEnum.ACCURACY,
        page: Int = DEFAULT_SEARCH_IMAGE_PAGE,
        size: Int = DEFAULT_SEARCH_IMAGE_SIZE
    ): SearchResultEntity
}
