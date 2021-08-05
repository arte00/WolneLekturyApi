package com.example.wolnelektury.utils

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wolnelektury.model.Book
import com.example.wolnelektury.model.Genre

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

@BindingAdapter("genreTitle")
fun TextView.setBookTitle(genre: Genre){
    text = genre.name
}

@BindingAdapter("isVisible")
fun RecyclerView.setVisibility(listIsNotEmpty: Boolean){
    visibility = when(listIsNotEmpty){
        true -> View.VISIBLE
        false -> View.GONE
    }
}

@BindingAdapter("imageIsVisible")
fun ImageView.setVisibility(listIsNotEmpty: Boolean){
    visibility = when(listIsNotEmpty){
        true -> View.GONE
        false -> View.VISIBLE
    }
}

@BindingAdapter("detailsCover")
fun ImageView.setDetailsCoverImage(imgUri: Uri?){
    Glide.with(context)
        .load(imgUri)
        .into(this)
}
