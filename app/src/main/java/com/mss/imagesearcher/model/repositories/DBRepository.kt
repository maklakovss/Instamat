package com.mss.imagesearcher.model.repositories

import com.mss.imagesearcher.model.entity.QueryParams
import io.reactivex.Maybe

interface DBRepository {

    fun getHistoryQueries(): Maybe<List<QueryParams>>

    fun addQueryInHistory(queryParams: QueryParams): Maybe<Int>

    fun clearHistory(): Maybe<Int>
}