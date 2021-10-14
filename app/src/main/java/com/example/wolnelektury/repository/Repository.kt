package com.example.wolnelektury.repository

import com.example.wolnelektury.api.RetrofitInstance
import com.example.wolnelektury.model.Book
import com.example.wolnelektury.model.BookDetails
import com.example.wolnelektury.model.Genre
import retrofit2.Response

class Repository {

    suspend fun getBookList(genre: String) : Response<List<Book>> {
        return RetrofitInstance.api.getBookList(genre)
    }

    suspend fun getExampleBookList() : Response<List<Book>> {
        return RetrofitInstance.api.getExampleBookList()
    }

    suspend fun getBookDetails(title: String) : Response<BookDetails> {
        return RetrofitInstance.api.getBookDetails(title)
    }

    suspend fun getGenres() : Response<List<Genre>> {
        return RetrofitInstance.api.getGenres()
    }

    suspend fun insert(){

    }

}