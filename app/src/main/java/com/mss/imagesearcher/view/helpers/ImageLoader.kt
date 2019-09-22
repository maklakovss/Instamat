package com.mss.imagesearcher.view.helpers

import android.content.Context
import android.widget.ImageView

interface ImageLoader {

    fun load(context: Context,
             path: String,
             imageView: ImageView,
             onSuccess: () -> Unit,
             onFailure: () -> Unit)
}
