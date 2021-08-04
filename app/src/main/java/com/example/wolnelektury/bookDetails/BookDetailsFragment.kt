package com.example.wolnelektury.bookDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.wolnelektury.databinding.FragmentBookDetailsBinding
import com.example.wolnelektury.databinding.FragmentBookListBinding
import com.example.wolnelektury.repository.Repository

class BookDetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookDetailsViewModel

    private val args: BookDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDetailsBinding.inflate(layoutInflater, container, false)

        val repository = Repository()
        Log.d("TAG", args.href)
        val viewModelFactory = BookDetailsViewModelFactory(repository, args.href)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookDetailsViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bookDetailsViewModel = viewModel

        return binding.root
    }

}