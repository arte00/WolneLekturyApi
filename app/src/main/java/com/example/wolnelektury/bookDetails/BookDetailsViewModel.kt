package com.example.wolnelektury.bookDetails

import android.net.Uri
import android.text.Html
import android.text.Html.fromHtml
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.wolnelektury.model.BookDetails
import com.example.wolnelektury.model.Epoch
import com.example.wolnelektury.model.Genre
import com.example.wolnelektury.repository.Repository
import com.example.wolnelektury.utils.setCoverImage
import kotlinx.coroutines.launch
import retrofit2.Response

class BookDetailsViewModel(private val repository: Repository, href: String) : ViewModel() {

    private val convertedHref = convertHref(href)

    val isLoading = MutableLiveData(false)

    private var _response = MutableLiveData<Response<BookDetails>>()
    private val response get() = _response

    val progressBarVisible = Transformations.map(isLoading){
        state -> when(state){
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    val title = Transformations.map(_response){
        response -> response.body()?.title.toString()
    }

    val description = Transformations.map(_response){
        response -> response.body()?.fragment_data?.html?.let {
            fromHtml(it)
        }
    }

    val imageUri = MutableLiveData<Uri>()

    private fun getUri(thumb: String): Uri? {
        var uri: Uri? = null
        viewModelScope.launch {
            uri = thumb.toUri().buildUpon().scheme("https").build()
        }
        return uri
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
            _response.value = response
            imageUri.value = getUri(response.body()?.cover_thumb!!)

            isLoading.value = false
        }
    }

    private fun convertHref(href: String): String {
        // To remake ... /books/CONTENT
        return href.substring(34)
    }

    private fun getListOfEpochs(epochs: List<Epoch>?): String {
        var string = "Epoka: "
        if (epochs != null) {
            for(epoch in epochs){
                string += "${epoch.name}, "
            }
        }
        return string
    }

    private fun getListOfGenres(genres: List<Genre>?): String {
        var string = "Gatunek literacki: "
        if (genres != null) {
            for(genre in genres){
                string += "${genre.name}, "
            }
        }
        return string
    }

}