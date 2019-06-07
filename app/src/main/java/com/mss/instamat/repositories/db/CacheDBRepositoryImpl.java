package com.mss.instamat.repositories.db;

import com.mss.instamat.model.CacheDBRepository;

public class CacheDBRepositoryImpl implements CacheDBRepository {

    private final CacheDB cacheDB;

    public CacheDBRepositoryImpl(CacheDB cacheDB) {
        this.cacheDB = cacheDB;
    }
}
