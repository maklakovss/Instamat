package com.mss.imagesearcher.model.repositories.network.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImagesResponse(

        @Expose
        @SerializedName("totalHits")
        var totalHits: Int = 0,

        @Expose
        @SerializedName("total")
        var total: Int = 0,

        @Expose
        @SerializedName("hits")
        var hits: List<ImageInfoNet>? = null
)
