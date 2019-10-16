package com.mss.imagesearcher.model.repositories.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mss.imagesearcher.model.repositories.db.entity.QueryParamsDB

@Database(entities = [QueryParamsDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun queryParamsDao(): QueryParamsDao

}