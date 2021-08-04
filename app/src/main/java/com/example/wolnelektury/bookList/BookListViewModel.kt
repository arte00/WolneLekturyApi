package com.example.wolnelektury.bookList

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wolnelektury.model.Book
import com.example.wolnelektury.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class BookListViewModel(private val repository: Repository) : ViewModel() {

    // Data

    val testDetails = MutableLiveData(false)

    private val _books = MutableLiveData<Response<List<Book>>>()
    val books get() = _books

    private val _title = MutableLiveData("Ballady")
    val title get() = _title


    // View operations

    private val _navigateToBookDetail = MutableLiveData<String>()
    val navigateToBookDetail
        get() = _navigateToBookDetail

    private val _isLoading = MutableLiveData(false)

    val progressBarLoading = Transformations.map(_isLoading){
        progress -> when(progress){
            false -> View.GONE
            true -> View.VISIBLE
        }
    }

    // FUNCTIONS

    init {
        getExampleBookList()
    }

    private fun getExampleBookList(){
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getExampleBookList()
            _books.value = response
            _isLoading.value = false
        }
    }

    private fun getBookList(){
        _isLoading.value = true
        viewModelScope.launch {
            Log.d("_TAG", "start")
            val response = repository.getBookList()
            _books.value = response
            Log.d("_TAG", "done")
        }
        _isLoading.value = false
    }

    fun onBookDetailsClicked(href: String){
        _navigateToBookDetail.value = href
    }

    fun onBookDetailsNavigated(){
        _navigateToBookDetail.value = null
    }

}