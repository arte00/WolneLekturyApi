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

    private val _books = MutableLiveData<Response<List<Book>>>()
    val books get() = _books

    private val _title = MutableLiveData("Ballady")
    val title get() = _title

    // View operations

    private val _navigateToBookDetail = MutableLiveData<String>()
    val navigateToBookDetail
        get() = _navigateToBookDetail

    private val isLoading = MutableLiveData(false)

    val progressBarLoading = Transformations.map(isLoading){
        state -> when(state){
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
            isLoading.value = true
            val response = repository.getExampleBookList()
            _books.value = response
            isLoading.value = false
        }
    }

    private fun getBookList(){
        isLoading.value = true
        viewModelScope.launch {
            val response = repository.getBookList()
            _books.value = response
        }
        isLoading.value = false
    }

    fun onBookDetailsClicked(href: String){
        _navigateToBookDetail.value = href
    }

    fun onBookDetailsNavigated(){
        _navigateToBookDetail.value = null
    }

}