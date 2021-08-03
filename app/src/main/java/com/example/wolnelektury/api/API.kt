package com.example.wolnelektury.api

import com.example.wolnelektury.model.Book
import com.example.wolnelektury.model.BookComplete
import retrofit2.Response
import retrofit2.http.GET

interface API {

    @GET("books/")
    suspend fun getBookList() : Response<List<Book>>

    @GET("books/studnia-i-wahadlo/")
    suspend fun getWell() : Response<BookComplete>

    @GET("authors/friedrich-nietzsche/parent_books/")
    suspend fun getExampleBookList() : Response<List<Book>>

}