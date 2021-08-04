package com.example.wolnelektury.api

import com.example.wolnelektury.model.Book
import com.example.wolnelektury.model.BookDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {

    @GET("books/")
    suspend fun getBookList() : Response<List<Book>>

    @GET("genres/ballada/parent_books/")
    suspend fun getExampleBookList() : Response<List<Book>>

    @GET("books/{title}")
    suspend fun getBookDetails(
        @Path("title") title2: String
    ) : Response<BookDetails>

}