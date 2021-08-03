package com.example.wolnelektury.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.wolnelektury.databinding.FragmentMainBinding
import com.example.wolnelektury.repository.Repository

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = MainAdapter()
        binding.listBooks.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner, {
            adapter.data = it.body()!!
        })


        return binding.root
    }

}