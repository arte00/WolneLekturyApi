package com.example.wolnelektury.bookList

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.wolnelektury.R
import com.example.wolnelektury.databinding.FragmentBookListBinding
import com.example.wolnelektury.repository.Repository

class BookListFragment : Fragment() {

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookListViewModel

    private val args: BookListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(layoutInflater, container, false)

        val repository = Repository()
        val viewModelFactory = BookListViewModelFactory(repository, args.href)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookListViewModel::class.java)
        binding.bookListViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpAdapter()
        navigateToBookDetailsObserver()

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
                adapter.addHeaderAndSubmitList(it.body())
                if (it.body() == null){
                    binding.listBooks.visibility = View.GONE
                    binding.imageEmptyList.visibility = View.VISIBLE
                } else {
                    binding.listBooks.visibility = View.VISIBLE
                    binding.imageEmptyList.visibility = View.GONE
                }
            }
        })
    }

}