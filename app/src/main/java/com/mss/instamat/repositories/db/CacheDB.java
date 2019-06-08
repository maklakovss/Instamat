package com.mss.instamat.repositories.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mss.instamat.repositories.db.models.ImageInfoDB;

@Database(entities = {ImageInfoDB.class}, version = 1)
public abstract class CacheDB extends RoomDatabase {

    public abstract ImageInfoDao productDao();
}
