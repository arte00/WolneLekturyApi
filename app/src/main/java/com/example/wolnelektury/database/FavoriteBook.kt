package com.example.wolnelektury.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wolnelektury.model.BookDetails

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0L,
    @ColumnInfo(name = "href")
    val href: String,
    @ColumnInfo(name = "book_details")
    val book: BookDetails
)
