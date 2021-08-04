package com.example.wolnelektury.bookList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.wolnelektury.R
import com.example.wolnelektury.databinding.FragmentBookListBinding
import com.example.wolnelektury.repository.Repository

class BookListFragment : Fragment() {

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(layoutInflater, container, false)

        val repository = Repository()
        val viewModelFactory = BookListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookListViewModel::class.java)
        binding.bookListViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpAdapter()
        navigateToBookDetailsObserver()

        return binding.root
    }

    private fun navigateToBookDetailsObserver() {
        viewModel.navigateToBookDetail.observe(viewLifecycleOwner, { book ->
            book?.let {
                this.findNavController().navigate(
                    BookListFragmentDirections
                        .actionBookListFragmentToBookDetailsFragment(book)
                )
                viewModel.onBookDetailsNavigated()
            }
        })
    }

    private fun setUpAdapter(){
        val adapter = BookListAdapter(BookListListener { href ->
            viewModel.onBookDetailsClicked(href)
        })
        binding.listBooks.adapter = adapter
        viewModel.books.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it.body())
            }
        })
    }

}