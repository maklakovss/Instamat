package com.mss.imagesearcher.repositories.files

import android.graphics.Bitmap
import android.os.Environment
import com.mss.imagesearcher.domain.models.ImageInfo
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class FilesHelper {

    companion object {
        private const val SAVE_FOLDER = "imagesearcher"
    }

    val folderPath: String
        get() = publicDirectory + File.separator + SAVE_FOLDER

    val publicDirectory: String
        get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath

    fun getFilePath(folder: String, imageInfo: ImageInfo): String {
        return folder + File.separator + imageInfo.id + ".jpg"
    }

    fun createFolderIfNotExist(folder: String) {
        File(folder).mkdirs()
    }

    @Throws(IOException::class)
    fun getFileOutputStream(filePath: String): OutputStream {
        return FileOutputStream(filePath)
    }

    fun deleteFileIfExist(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            Timber.d("Delete file in %s", filePath)
            file.delete()
        }
    }

    @Throws(IOException::class)
    fun saveBitmapToStream(bitmap: Bitmap, outStream: OutputStream) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
    }
}
