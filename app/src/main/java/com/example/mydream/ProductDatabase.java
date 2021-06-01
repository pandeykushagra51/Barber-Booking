package com.example.mydream;


import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Product.class, ProdcutFts.class},
        exportSchema = false,
        version = 2 )
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase INSTANCE;

    public abstract ProductDao productDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

    public static ProductDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product-database")
                    .allowMainThreadQueries().addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }
}

