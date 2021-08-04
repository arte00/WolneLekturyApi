package com.example.wolnelektury.bookDetails

import android.util.Log
import androidx.lifecycle.*
import com.example.wolnelektury.model.BookDetails
import com.example.wolnelektury.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class BookDetailsViewModel(private val repository: Repository, href: String) : ViewModel() {

    val isLoading = MutableLiveData(false)

    private var _response = MutableLiveData<Response<BookDetails>>()
    private val response get() = _response

    val title = Transformations.map(_response){
        response -> response.body()?.title.toString()
    }
    val author = Transformations.map(response){
            newResponse -> newResponse.body()?.authors?.get(0).toString()
    }
    val epochs = Transformations.map(response){
            newResponse -> newResponse.body()?.epochs?.get(0).toString()
    }
    val genres = Transformations.map(response){
            newResponse -> newResponse.body()?.genres?.get(0).toString()
    }

    init {
        getBookDetails(href)
    }

    private fun getBookDetails(title: String){
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getBookDetails(title)
            Log.d("_TAG", response.toString())
            response.body()?.let { Log.d("_TAG", it.title) }
            _response.value = response
            isLoading.value = false
        }
    }

}