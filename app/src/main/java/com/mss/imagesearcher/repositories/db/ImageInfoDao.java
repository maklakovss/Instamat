package com.mss.imagesearcher.repositories.db;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mss.imagesearcher.repositories.db.models.ImageInfoDB;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ImageInfoDao {

    @NonNull
    @Query("SELECT * FROM ImageInfoDB WHERE query = :query and page = :page")
    Single<List<ImageInfoDB>> getImagesInfo(@NonNull final String query, int page);

    @NonNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(@NonNull final ImageInfoDB imageInfo);

    @NonNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
