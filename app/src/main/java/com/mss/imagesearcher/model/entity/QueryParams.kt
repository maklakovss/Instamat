package com.mss.imagesearcher.model.entity


data class QueryParams(
        var query: String? = "",
        var imageType: String? = "all",
        var orientation: String? = "all",
        var category: String? = null,
        var minWidth: Int? = null,
        var minHeight: Int? = null,
        var colors: String? = null,
        var imageOrder: String? = "popular"
)
