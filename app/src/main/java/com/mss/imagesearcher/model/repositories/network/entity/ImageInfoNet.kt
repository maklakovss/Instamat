package com.mss.imagesearcher.model.repositories.network.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageInfoNet(

        @Expose
        @SerializedName("id")
        var id: Long = 0,

        @Expose
        @SerializedName("largeImageURL")
        var largeImageURL: String? = null,

        @Expose
        @SerializedName("previewURL")
        var previewURL: String? = null,

        @Expose
        @SerializedName("webformatURL")
        var webFormatURL: String? = null,

        @Expose
        @SerializedName("imageHeight")
        var height: Int = 0,

        @Expose
        @SerializedName("imageWidth")
        var width: Int = 0,

        @Expose
        @SerializedName("type")
        var type: String? = null,

        @Expose
        @SerializedName("tags")
        var tags: String? = null,

        @Expose
        @SerializedName("pageURL")
        var pageUrl: String? = null,

        @Expose
        @SerializedName("likes")
        var likes: Int = 0,

        @Expose
        @SerializedName("views")
        var views: Int = 0,

        @Expose
        @SerializedName("comments")
        var comments: Int = 0
)