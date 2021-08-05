package com.example.wolnelektury.bookList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wolnelektury.repository.Repository

class BookListViewModelFactory(private val repository: Repository, private val href: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookListViewModel(repository, href) as T
    }
}