package com.mss.imagesearcher.model.repositories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mss.imagesearcher.model.repositories.db.entity.QueryParamsDB
import io.reactivex.Single

@Dao
interface HistoryDao {

    @Query("SELECT * FROM QueryParams ORDER BY dataUsing DESC")
    fun getAll(): Single<List<QueryParamsDB>>

    @Query("DELETE FROM QueryParams")
    fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: QueryParamsDB): Long

}