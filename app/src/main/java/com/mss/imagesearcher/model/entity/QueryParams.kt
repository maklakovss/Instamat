package com.mss.imagesearcher.model.entity


data class QueryParams(
        var query: String,
        var imageType: String? = null,
        var orientation: String? = null,
        var category: String? = null,
        var minWidth: Int? = null,
        var minHeight: Int? = null,
        var colors: String? = null,
        var imageOrder: String? = null
)
