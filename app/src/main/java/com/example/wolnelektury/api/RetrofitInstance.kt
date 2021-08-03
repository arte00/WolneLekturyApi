package com.example.wolnelektury.api

import com.example.wolnelektury.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: API by lazy {
        retrofit.create(API::class.java)
    }

}