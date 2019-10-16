package com.mss.imagesearcher.model.repositories

import com.mss.imagesearcher.model.entity.QueryParams
import io.reactivex.Single

interface DBRepository {

    fun getHistoryQueries(): Single<List<QueryParams>>

    fun addQueryInHistory(queryParams: QueryParams): Single<Long>

    fun clearHistory(): Single<Int>
}