package com.kakaopay.core.repository.search

import com.kakaopay.core.entity.ImageSearchEntity
import com.kakaopay.core.model.search.KAKAO_REST_KEY
import com.kakaopay.core.remote.ImageSearchRemoteDataSource

class ImageSearchRepositoryImpl(
    private val remote: ImageSearchRemoteDataSource
) : ImageSearchRepository {


    override suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ImageSearchEntity {
        val header = "KakaoAK $KAKAO_REST_KEY"
        return remote.requestImageSearch(header, query, sort, page, size)
            .toSearchResultEntity()
    }


}
