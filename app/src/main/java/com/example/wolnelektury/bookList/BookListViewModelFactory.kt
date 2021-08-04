package com.example.wolnelektury.bookList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wolnelektury.repository.Repository

class BookListViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookListViewModel(repository) as T
    }
}