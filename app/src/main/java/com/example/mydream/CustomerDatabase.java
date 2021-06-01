package com.example.mydream;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Customer.class}, version = 3)
public abstract class CustomerDatabase extends RoomDatabase {
    private static CustomerDatabase INSTANCE;

    public abstract CustomerDao CustomerDao();

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

    public static CustomerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CustomerDatabase.class, "user-database")
                            .allowMainThreadQueries().addMigrations(MIGRATION_2_3)
                            .build();
        }
        return INSTANCE;
    }
}
