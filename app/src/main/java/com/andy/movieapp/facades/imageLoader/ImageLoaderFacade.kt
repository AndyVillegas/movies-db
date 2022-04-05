package com.andy.movieapp.facades.imageLoader

import android.content.Context
import android.widget.ImageView

class ImageLoaderFacade(private val context: Context) {
    private var imageLoader: IImageLoader = GlideImageLoader(context)

    fun load(imageView: ImageView, url: String, onLoadingFinished: () -> Unit = {}){
        imageLoader.load(imageView, url, onLoadingFinished)
    }
}