package com.mss.imagesearcher.model.entity


data class QueryParams(
        var query: String,
        var imageType: String?,
        var orientation: String?,
        var category: String?,
        var minWidth: Int?,
        var minHeight: Int?,
        var colors: String?,
        var imageOrder: String?
)
