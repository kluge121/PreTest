package com.kakaopay.kakaopaypretest

data class SearchResult(
        var documents: MutableList<ImageItem>,
        var meta: Meta

)

data class Meta(
        var total_count: Int,
        var pageable_count: Int,
        var is_end: Boolean

)

data class ImageItem(
        var collection: String,
        var thumbnail_url: String,
        var image_url: String,
        var width: Int,
        var height: Int,
        var display_sitename: String,
        var doc_url: String,
        var datetime: String
)
