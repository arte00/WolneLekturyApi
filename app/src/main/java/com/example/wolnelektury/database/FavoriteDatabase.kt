package com.example.wolnelektury.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteBook::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract val favoriteDatabase: FavoriteDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context) : FavoriteDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java,
                        "favorite_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }

                return instance
            }
        }
    }

}