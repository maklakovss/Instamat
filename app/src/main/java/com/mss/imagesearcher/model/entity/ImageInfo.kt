package com.mss.imagesearcher.model.entity

data class ImageInfo(

        var id: Long = 0,

        var largeImageURL: String,

        var previewURL: String,

        var webFormatURL: String,

        var height: Int = 0,

        var width: Int = 0,

        var type: String,

        var tags: String,

        var pageUrl: String,

        var likes: Int = 0,

        var views: Int = 0,

        var comments: Int = 0
)
