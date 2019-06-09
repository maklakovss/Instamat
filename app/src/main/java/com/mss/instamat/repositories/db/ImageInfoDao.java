package com.mss.instamat.repositories.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.mss.instamat.repositories.db.models.ImageInfoDB;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ImageInfoDao {

    @NonNull
    @Query("SELECT * FROM ImageInfoDB WHERE query = :query and page = :page")
    Single<List<ImageInfoDB>> getImagesInfo(@NonNull final String query, int page);

    @NonNull
    @Insert
    Long insert(@NonNull final ImageInfoDB imageInfo);

    @NonNull
    @Insert
    List<Long> insertAll(@NonNull final List<ImageInfoDB> images);

    @Delete
    int delete(@NonNull final ImageInfoDB image);

    @Query("DELETE FROM ImageInfoDB")
    void deleteAll();

    @Query("DELETE FROM ImageInfoDB where query = :query")
    int deleteQuery(@NonNull final String query);

    @Query("DELETE FROM ImageInfoDB where query = :query AND page = :page")
    int deleteQuery(@NonNull final String query, int page);

    @Update
    int update(@NonNull final ImageInfoDB imageInfoDB);

    @Update
    int update(@NonNull final List<ImageInfoDB> imagesDB);
}
