package com.mss.imagesearcher.domain.repositories

import android.graphics.Bitmap

import com.mss.imagesearcher.domain.models.ImageInfo

import java.io.IOException

interface FilesRepository {

    @Throws(IOException::class)
    fun saveBitmap(imageInfo: ImageInfo, bitmap: Bitmap): String
}
