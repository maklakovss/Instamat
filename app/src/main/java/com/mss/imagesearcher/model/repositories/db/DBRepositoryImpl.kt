package com.mss.imagesearcher.model.repositories.db

import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.model.repositories.DBRepository
import io.reactivex.Maybe

class DBRepositoryImpl(val appDatabase: AppDatabase) : DBRepository {
    override fun getHistoryQueries(): Maybe<List<QueryParams>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addQueryInHistory(queryParams: QueryParams): Maybe<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearHistory(): Maybe<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}