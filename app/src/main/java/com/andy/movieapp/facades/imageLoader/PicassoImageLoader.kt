package com.andy.movieapp.facades.imageLoader

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoImageLoader: IImageLoader {
    override fun load(imageView: ImageView, url: String, onLoadingFinished: () -> Unit) {
        Picasso.get()
            .load(url)
            .placeholder(ColorDrawable(Color.BLACK))
            .into(imageView)
    }
}