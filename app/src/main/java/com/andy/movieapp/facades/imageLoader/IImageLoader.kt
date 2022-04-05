package com.andy.movieapp.facades.imageLoader

import android.widget.ImageView

interface IImageLoader {
    fun load(imageView: ImageView, url: String, onLoadingFinished: () -> Unit = {})
}