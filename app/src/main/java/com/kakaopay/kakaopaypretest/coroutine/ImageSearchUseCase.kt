package com.kakaopay.kakaopaypretest.coroutine

import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.model.SearchResultEntity

class ImageSearchUseCase(
    private val repository: ImageSearchRepository
) {

    suspend operator fun invoke(

        query: String,
        sortEnum: KakaoImageSearchSortEnum,
        page: Int,
        size: Int
    ): SearchResultEntity {
        return repository.getImageSearchResult(query, sortEnum, page, size)
    }


}