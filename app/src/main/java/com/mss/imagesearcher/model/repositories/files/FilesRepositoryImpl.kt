package com.mss.imagesearcher.model.repositories.files

import android.graphics.Bitmap
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.model.repositories.FilesRepository
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(private val filesHelper: FilesHelper) : FilesRepository {

    @Throws(IOException::class)
    override fun saveBitmap(imageInfo: ImageInfo, bitmap: Bitmap): String {
        Timber.d("Save bitmap '%s' started", imageInfo.id)
        val folder = filesHelper.folderPath
        filesHelper.createFolderIfNotExist(folder)
        val filePath = filesHelper.getFilePath(folder, imageInfo)
        filesHelper.deleteFileIfExist(filePath)
        val outStream = filesHelper.getFileOutputStream(filePath)
        filesHelper.saveBitmapToStream(bitmap, outStream)
        Timber.d("Done save bitmap '%s' to %s", imageInfo.id, filePath)
        return filePath
    }
}
