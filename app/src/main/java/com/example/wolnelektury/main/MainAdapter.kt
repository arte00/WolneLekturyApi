package com.example.wolnelektury.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wolnelektury.R
import com.example.wolnelektury.model.Book

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var data =  listOf<Book>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textTitle: TextView = itemView.findViewById(R.id.text_title)
        val textAuthor: TextView = itemView.findViewById(R.id.text_author)
        val textGenre: TextView = itemView.findViewById(R.id.text_genre)
        val imageCover: ImageView = itemView.findViewById(R.id.image_cover)

        fun bind(item: Book) {
            textTitle.text = item.title
            textAuthor.text = item.author
            textGenre.text = item.genre
            imageCover.setImageResource(R.drawable.book_simple_template)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_main, parent, false)

                return ViewHolder(view)
            }
        }
    }
}