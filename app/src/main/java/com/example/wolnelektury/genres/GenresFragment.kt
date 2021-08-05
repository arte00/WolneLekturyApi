package com.example.wolnelektury.genres

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wolnelektury.R
import com.example.wolnelektury.bookList.BookListAdapter
import com.example.wolnelektury.bookList.BookListListener
import com.example.wolnelektury.databinding.FragmentGenresBinding
import com.example.wolnelektury.repository.Repository


class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GenresViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenresBinding.inflate(layoutInflater, container, false)
        val repository = Repository()
        val viewModelFactory = GenresViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenresViewModel::class.java)
        binding.genresViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpAdapter()

        viewModel.navigateToBookList.observe(viewLifecycleOwner, { list ->
            list?.let {
                this.findNavController().navigate(
                    GenresFragmentDirections
                        .actionGenresFragmentToBookListFragment(list))
                viewModel.onNavigateToBookListFinished()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setUpAdapter(){
        val adapter = GenresAdapter(GenreListener {
            href -> viewModel.onNavigateToBookList(href)
        })
        binding.recyclerViewGenres.adapter = adapter
        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerViewGenres.layoutManager = manager
        viewModel.genres.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it.body())
            }
        })
    }

}