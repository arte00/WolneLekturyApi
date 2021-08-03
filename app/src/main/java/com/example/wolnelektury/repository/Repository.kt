package com.example.wolnelektury.repository

import com.example.wolnelektury.api.RetrofitInstance
import com.example.wolnelektury.model.Book
import com.example.wolnelektury.model.BookComplete
import retrofit2.Response

class Repository {

    suspend fun getBookList() : Response<List<Book>> {
        return RetrofitInstance.api.getBookList()
    }

    suspend fun getWell() : Response<BookComplete>{
        return RetrofitInstance.api.getWell()
    }

    suspend fun getExampleBookList() : Response<List<Book>> {
        return RetrofitInstance.api.getExampleBookList()
    }

}