package com.example.wolnelektury.database

import androidx.room.*

class FavoriteDatabaseDao {

    @Dao
    interface FavoriteDatabaseDao{

        @Insert
        fun insert(favoriteBook: FavoriteBook)

        @Update
        fun update(favoriteBook: FavoriteBook)

        @Query("DELETE FROM favorite_books")
        fun clear()

        @Query("SELECT * FROM favorite_books where href = :href LIMIT 1")
        fun checkFavorite(href: String): FavoriteBook?

    }

}