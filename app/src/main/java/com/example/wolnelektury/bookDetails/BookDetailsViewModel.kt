package com.example.wolnelektury.bookDetails

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.wolnelektury.model.BookDetails
import com.example.wolnelektury.model.Epoch
import com.example.wolnelektury.model.Genre
import com.example.wolnelektury.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class BookDetailsViewModel(private val repository: Repository, href: String) : ViewModel() {

    private val convertedHref = convertHref(href)

    private val isLoading = MutableLiveData(false)

    private var _response = MutableLiveData<Response<BookDetails>>()
    private val response get() = _response

    val isProgressBarVisible = Transformations.map(isLoading){
        state -> when(state){
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    val title = Transformations.map(_response){
        response -> response.body()?.title.toString()
    }
    val author = Transformations.map(response){
            newResponse -> newResponse.body()?.authors?.get(0)?.name
    }
    val epochs = Transformations.map(response){
            newResponse -> getListOfEpochs(newResponse.body()?.epochs)
    }

    val genres = Transformations.map(response){
            newResponse -> getListOfGenres(newResponse.body()?.genres)
    }

    init {
        getBookDetails(convertedHref)
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

    private fun convertHref(href: String): String {
        // To remake ... /books/CONTENT
        return href.substring(34)
    }

    private fun getListOfEpochs(epochs: List<Epoch>?): String {
        var string = ""
        if (epochs != null) {
            for(epoch in epochs){
                string += "${epoch.name}, "
            }
        }
        return string
    }

    private fun getListOfGenres(genres: List<Genre>?): String {
        var string = ""
        if (genres != null) {
            for(genre in genres){
                string += "${genre.name}, "
            }
        }
        return string
    }

}