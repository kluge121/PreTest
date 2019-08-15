package com.kakaopay.kakaopaypretest.model

import android.os.Parcel
import android.os.Parcelable

data class SearchResult(
        var documents: MutableList<ImageItem>,
        var meta: Meta?

) : Parcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(ImageItem.CREATOR)!!,
            source.readParcelable<Meta>(Meta::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(documents)
        writeParcelable(meta, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SearchResult> = object : Parcelable.Creator<SearchResult> {
            override fun createFromParcel(source: Parcel): SearchResult = SearchResult(source)
            override fun newArray(size: Int): Array<SearchResult?> = arrayOfNulls(size)
        }
    }
}

data class Meta(
        var total_count: Int,
        var pageable_count: Int,
        var is_end: Boolean

) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(total_count)
        writeInt(pageable_count)
        writeInt((if (is_end) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Meta> = object : Parcelable.Creator<Meta> {
            override fun createFromParcel(source: Parcel): Meta = Meta(source)
            override fun newArray(size: Int): Array<Meta?> = arrayOfNulls(size)
        }
    }
}

data class ImageItem(
        var collection: String,
        var thumbnail_url: String,
        var image_url: String,
        var width: Int,
        var height: Int,
        var display_sitename: String,
        var doc_url: String,
        var datetime: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readInt(),
            source.readInt(),
            source.readString()!!,
            source.readString()!!,
            source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(collection)
        writeString(thumbnail_url)
        writeString(image_url)
        writeInt(width)
        writeInt(height)
        writeString(display_sitename)
        writeString(doc_url)
        writeString(datetime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImageItem> = object : Parcelable.Creator<ImageItem> {
            override fun createFromParcel(source: Parcel): ImageItem = ImageItem(source)
            override fun newArray(size: Int): Array<ImageItem?> = arrayOfNulls(size)
        }
    }
}
