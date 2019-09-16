package com.kakaopay.core.domain

import com.kakaopay.core.entity.ImageSearchEntity
import com.kakaopay.core.model.search.KakaoImageSearchSortEnum
import com.kakaopay.core.repository.search.ImageSearchRepository


class ImageSearchUseCase(
    private val repository: ImageSearchRepository
) {

    suspend operator fun invoke(
        query: String,
        sortEnum: KakaoImageSearchSortEnum,
        page: Int,
        size: Int
    ): ImageSearchEntity {
        return repository.getImageSearchResult(query, sortEnum.toString(), page, size)
    }


}