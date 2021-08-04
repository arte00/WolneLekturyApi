package com.example.wolnelektury.bookList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wolnelektury.R
import com.example.wolnelektury.databinding.ItemBookListBinding
import com.example.wolnelektury.model.Book

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class BookListAdapter(private val clickListener: BookListListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(BookListDiffCallback()) {


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.BookItem -> ITEM_VIEW_TYPE_ITEM
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {

        companion object {

            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_book_list, parent, false)
                return TextViewHolder(view)
            }
        }

    }

    class ViewHolder private constructor(private val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Book, clickListener: BookListListener) {
            binding.book = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBookListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val bookItem = getItem(position) as DataItem.BookItem
                holder.bind(bookItem.book, clickListener)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<Book>?) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.BookItem(it) }
        }
        submitList(items)
    }

}

class BookListDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class BookListListener(val clickListener: (href: String) -> Unit) {
    fun onClick(book: Book) = clickListener(book.href)
}

sealed class DataItem {

    abstract val id: String

    data class BookItem(val book: Book): DataItem() {
        override val id: String
            get() = book.href
    }

    object Header: DataItem() {
        override val id: String
            get() = "header"
    }
}