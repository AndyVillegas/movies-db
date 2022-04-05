package com.andy.movieapp.facades.imageLoader

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class GlideImageLoader(private val context: Context): IImageLoader {

    override fun load(imageView: ImageView, url: String, onLoadingFinished: () -> Unit) {
        Glide.with(context)
            .load(url)
            .placeholder(ColorDrawable(Color.BLACK))
            .apply(
                RequestOptions()
                    .dontTransform()
            )
            .override(Target.SIZE_ORIGINAL)
            .listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoadingFinished()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoadingFinished()
                    return false
                }

            })
            .into(imageView)
    }
}