package com.mss.imagesearcher.model.repositories.db

import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.model.repositories.DBRepository
import com.mss.imagesearcher.model.repositories.db.entity.QueryParamsDB
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class DBRepositoryImpl(val appDatabase: AppDatabase) : DBRepository {

    override fun getHistoryQueries(): Single<List<QueryParams>> =
            appDatabase.queryParamsDao().getAll()
                    .map { mapListFromDB(it) }
                    .subscribeOn(Schedulers.io())

    override fun addQueryInHistory(queryParams: QueryParams): Single<Long> = Single.create<Long> {
        it.onSuccess(appDatabase.queryParamsDao().insert(mapToDB(queryParams)))

    }
            .subscribeOn(Schedulers.io())

    override fun clearHistory(): Single<Int> = Single.create<Int> {
        it.onSuccess(appDatabase.queryParamsDao().deleteAll())
    }
            .subscribeOn(Schedulers.io())

    private fun mapListFromDB(paramsDB: List<QueryParamsDB>): List<QueryParams> =
            paramsDB.map { mapFromDB(it) }

    private fun mapFromDB(queryParamsDB: QueryParamsDB): QueryParams = QueryParams(
            queryParamsDB.query,
            queryParamsDB.imageType,
            queryParamsDB.orientation,
            queryParamsDB.category,
            queryParamsDB.minWidth,
            queryParamsDB.minHeight,
            queryParamsDB.colors,
            queryParamsDB.imageOrder
    )

    private fun mapToDB(queryParams: QueryParams): QueryParamsDB = QueryParamsDB(
            Date().time,
            queryParams.toString(),
            queryParams.query,
            queryParams.imageType,
            queryParams.orientation,
            queryParams.category,
            queryParams.minWidth,
            queryParams.minHeight,
            queryParams.colors,
            queryParams.imageOrder
    )

}