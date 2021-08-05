package com.example.wolnelektury.genres

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wolnelektury.model.Genre
import com.example.wolnelektury.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class GenresViewModel(val repository: Repository) : ViewModel() {

    val genres = MutableLiveData<Response<List<Genre>>>()

    private val _navigateToBookList = MutableLiveData<String>()
    val navigateToBookList get() = _navigateToBookList

    init {
        getGenres()
    }

    private fun getGenres(){
        viewModelScope.launch {
            val response = repository.getGenres()
            genres.value = response
        }
    }

    fun onNavigateToBookList(href: String){
        _navigateToBookList.value = href
    }

    fun onNavigateToBookListFinished(){
        _navigateToBookList.value = null
    }

}