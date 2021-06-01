package com.example.mydream;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Seller.class}, version = 2)
public abstract class SellerDatabase extends RoomDatabase {
    private static SellerDatabase INSTANCE;

    public abstract SellerDao SellerDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

    public static SellerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SellerDatabase.class, "seller-database")
                    .allowMainThreadQueries().addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }
}
