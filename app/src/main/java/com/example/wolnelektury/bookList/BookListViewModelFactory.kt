package com.example.wolnelektury.bookList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wolnelektury.database.FavoriteDatabaseDao
import com.example.wolnelektury.repository.Repository

class BookListViewModelFactory(private val repository: Repository,
                               private val database: FavoriteDatabaseDao,
                               private val href: String,
                               private val application: Application
                               ) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookListViewModel(repository, database, href, application) as T
    }
}