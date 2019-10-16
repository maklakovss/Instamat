package com.mss.imagesearcher.model.repositories.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QueryParams")
data class QueryParamsDB(

        var id: Long,

        @PrimaryKey
        var hash: String,

        var query: String?,

        var imageType: String?,

        var orientation: String?,

        var category: String?,

        var minWidth: Int?,

        var minHeight: Int?,

        var colors: String?,

        var imageOrder: String?
)
