package com.example.wolnelektury.bookList

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.wolnelektury.database.FavoriteDatabaseDao
import com.example.wolnelektury.model.Book
import com.example.wolnelektury.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class BookListViewModel(private val repository: Repository,
                        private val database: FavoriteDatabaseDao,
                        private val href: String,
                        application: Application
                        ) : AndroidViewModel(application) {

    // Data

    private val hrefConverted = convertHref(href)

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
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    // FUNCTIONS

    init {
        getBookList(hrefConverted)
    }

    private fun getBookList(genre: String){
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getBookList(genre)
            _books.value = response
            Log.d("_TAG", response.toString())
            isLoading.value = false
        }
    }

    fun onBookDetailsClicked(href: String){
        _navigateToBookDetail.value = href
    }

    fun onBookDetailsNavigated(){
        _navigateToBookDetail.value = null
    }

    private fun convertHref(href: String): String {
        // To remake ... /books/CONTENT
        return href.substring(35)
    }

}