package com.mss.imagesearcher.repositories.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mss.imagesearcher.repositories.db.models.ImageInfoDB;

@Database(entities = {ImageInfoDB.class}, version = 1)
public abstract class CacheDB extends RoomDatabase {

    @NonNull
    public abstract ImageInfoDao productDao();
}
