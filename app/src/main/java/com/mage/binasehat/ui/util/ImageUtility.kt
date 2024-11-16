package com.mage.binasehat.ui.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object ImageUtility {

    // Converts Uri to File
    fun uriToFile(uri: Uri, context: Context): File? {
        val contentResolver: ContentResolver = context.contentResolver
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

        try {
            // Open an InputStream from the Uri
            inputStream = contentResolver.openInputStream(uri)
            if (inputStream == null) {
                return null // Can't read input stream
            }

            // Create an OutputStream to the temporary file
            outputStream = FileOutputStream(tempFile)

            // Write the content from the InputStream to the temporary file
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            // Return the temporary file
            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}