package com.kakaopay.core.repository.search

import com.kakaopay.core.entity.ImageSearchEntity


interface ImageSearchRepository {

    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ImageSearchEntity
}
