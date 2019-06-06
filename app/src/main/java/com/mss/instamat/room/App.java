package com.mss.instamat.room;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    private static final String DATABASE_NAME = "USERS";
    private static UsersDatabase database;
    private static App INSTANCE;

    public static UsersDatabase getDatabase() {
        return database;
    }

    public static App getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        database = Room.databaseBuilder(getApplicationContext(), UsersDatabase.class, DATABASE_NAME).build();
    }
}
