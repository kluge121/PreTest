package com.kakaopay.core.model.search

import com.google.gson.annotations.SerializedName
import com.kakaopay.core.entity.ImageItem
import com.kakaopay.core.entity.ImageSearchEntity
import com.kakaopay.core.entity.Meta


data class ImageSearchResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val documents: MutableList<ImageItem>
) {

    fun toSearchResultEntity() = ImageSearchEntity(
        meta = meta,
        documents = documents
    )

    fun toSearchImageListEntity(): MutableList<ImageItem> = documents


    fun toMetaEntity() : Meta = meta


}