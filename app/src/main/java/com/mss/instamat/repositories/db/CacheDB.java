package com.mss.instamat.repositories.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.mss.instamat.repositories.db.models.ImageInfoDB;

@Database(entities = {ImageInfoDB.class}, version = 1)
public abstract class CacheDB extends RoomDatabase {

    @NonNull
    public abstract ImageInfoDao productDao();
}
