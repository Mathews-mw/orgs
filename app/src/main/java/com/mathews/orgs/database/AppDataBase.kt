package com.mathews.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mathews.orgs.database.converter.Converters
import com.mathews.orgs.database.dao.ProductDAO
import com.mathews.orgs.model.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDAO;

    companion object {
        @Volatile
        private lateinit var db: AppDataBase

        fun instanceDb(context: Context): AppDataBase {
            if (::db.isInitialized) return db;


            db = Room.databaseBuilder(context, AppDataBase::class.java, "orgs.db")
                .allowMainThreadQueries()
                .build();

            return db;
        }
    }
}