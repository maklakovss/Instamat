package com.mss.imagesearcher.model.repositories

import android.graphics.Bitmap

import com.mss.imagesearcher.model.entity.ImageInfo

import java.io.IOException

interface FilesRepository {

    @Throws(IOException::class)
    fun saveBitmap(imageInfo: ImageInfo, bitmap: Bitmap): String
}
