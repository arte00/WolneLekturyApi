package com.example.wolnelektury.bookDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wolnelektury.repository.Repository

class BookDetailsViewModelFactory(private val repository: Repository, private val href: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookDetailsViewModel(repository, href) as T
    }
}