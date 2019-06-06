package com.mss.instamat.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface RoomUserDao {

    @Query("SELECT * FROM user")
    Single<List<User>> getAll();

    @Query("SELECT * FROM user where id = :id")
    Single<User> findUserById(int id);

    @Insert
    Long insert(User user);

    @Insert
    List<Long> insertAll(List<User> users);

    @Update
    int update(User user);

    @Delete
    int delete(User user);

    @Query("DELETE FROM user")
    int deleteAll();
}
