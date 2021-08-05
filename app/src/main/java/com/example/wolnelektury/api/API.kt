package com.example.wolnelektury.api

import com.example.wolnelektury.model.Book
import com.example.wolnelektury.model.BookDetails
import com.example.wolnelektury.model.Genre
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {

    @GET("genres/{genre}parent_books")
    suspend fun getBookList(@Path("genre") genre: String) : Response<List<Book>>

    @GET("genres/ballada/parent_books/")
    suspend fun getExampleBookList() : Response<List<Book>>

    @GET("books/{title}")
    suspend fun getBookDetails(
        @Path("title") title2: String
    ) : Response<BookDetails>

    @GET("genres/")
    suspend fun getGenres() : Response<List<Genre>>

}