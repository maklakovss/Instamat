package com.mss.instamat;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.repositories.db.CacheDB;
import com.mss.instamat.repositories.db.CacheDBRepositoryImpl;

public class App extends Application {

    private static final String DATABASE_NAME = "USERS";
    private static CacheDBRepository cacheDBRepository;
    private static App INSTANCE;

    public static CacheDBRepository getCacheDBRepository() {
        return cacheDBRepository;
    }

    public static App getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cacheDBRepository = new CacheDBRepositoryImpl(Room.databaseBuilder(getApplicationContext(), CacheDB.class, DATABASE_NAME).build());
    }
}
