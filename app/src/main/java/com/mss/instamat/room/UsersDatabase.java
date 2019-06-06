package com.mss.instamat.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    public abstract RoomUserDao productDao();
}
