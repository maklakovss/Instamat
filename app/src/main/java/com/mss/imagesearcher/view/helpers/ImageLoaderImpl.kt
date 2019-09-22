package com.mss.imagesearcher.view.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import timber.log.Timber

class ImageLoaderImpl : ImageLoader {

    override fun load(context: Context,
                      path: String,
                      imageView: ImageView,
                      onSuccess: () -> Unit,
                      onFailure: () -> Unit) {
        Glide
                .with(context)
                .load(path)
                .addListener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Timber.e(e)
                        onFailure()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Timber.d("Image loaded")
                        onSuccess()
                        return false
                    }
                })
                .into(imageView)
    }
}
