package com.mss.imagesearcher.model.entity

data class ImageInfo(

        var id: Long = 0,

        var largeImageURL: String? = null,

        var previewURL: String? = null,

        var webFormatURL: String? = null,

        var height: Int = 0,

        var width: Int = 0,

        var type: String? = null,

        var tags: String? = null,

        var pageUrl: String? = null,

        var likes: Int = 0,

        var views: Int = 0,

        var comments: Int = 0
)
