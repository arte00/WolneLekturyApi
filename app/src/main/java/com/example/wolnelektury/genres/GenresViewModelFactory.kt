package com.example.wolnelektury.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wolnelektury.repository.Repository

class GenresViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GenresViewModel(repository) as T
    }
}