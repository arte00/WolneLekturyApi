package com.example.wolnelektury.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.wolnelektury.R
import com.example.wolnelektury.model.Book

@BindingAdapter("coverImage")
fun ImageView.setCoverImage(book: Book){
    val imgUri = book.simple_thumb.toUri()
        .buildUpon()
        .scheme("https")
        .build()
    Glide.with(context)
        .load(imgUri)
        .into(this)
}

@BindingAdapter("bookTitle")
fun TextView.setBookTitle(book: Book){
    text = book.title
}

@BindingAdapter("bookAuthor")
fun TextView.setBookAuthor(book: Book){
    text = book.author
}

@BindingAdapter("bookGenre")
fun TextView.setBookGenre(book: Book){
    text = book.genre
}
