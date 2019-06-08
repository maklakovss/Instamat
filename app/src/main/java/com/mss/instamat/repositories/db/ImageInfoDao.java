package com.mss.instamat.repositories.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mss.instamat.repositories.db.models.ImageInfoDB;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ImageInfoDao {

    @Query("SELECT * FROM ImageInfoDB WHERE 'query' = :query and page = :page")
    Single<List<ImageInfoDB>> getImagesInfo(String query, int page);

    @Insert
    Long insert(ImageInfoDB imageInfo);

    @Insert
    List<Long> insertAll(List<ImageInfoDB> images);

    @Delete
    int delete(ImageInfoDB image);

    @Query("DELETE FROM ImageInfoDB")
    void deleteAll();

    @Query("DELETE FROM ImageInfoDB where 'query' = :query")
    int deleteQuery(String query);

    @Query("DELETE FROM ImageInfoDB where 'query' = :query AND page = :page")
    int deleteQuery(String query, int page);

    @Update
    int update(ImageInfoDB imageInfoDB);

    @Update
    int update(List<ImageInfoDB> imagesDB);
}
