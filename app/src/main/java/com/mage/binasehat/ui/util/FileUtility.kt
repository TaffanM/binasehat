package com.mage.binasehat.ui.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtility {

    // Function to convert content URI to RequestBody
    fun uriToRequestBody(context: Context, uri: Uri): RequestBody {
        // Get the InputStream from the URI
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

        // Create a temporary file to write the InputStream data into
        val file = File(context.cacheDir, "temp_image.jpg") // You can give it any name

        val outputStream = FileOutputStream(file)

        // Read from InputStream and write to temporary file
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        // Now create the RequestBody
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
        return file.asRequestBody(mimeType.toMediaTypeOrNull()) // Assuming it's an image
    }

    // Helper function to create MultipartBody.Part from URI
    fun createMultipartFromUri(context: Context, uri: Uri, partName: String = "photo"): MultipartBody.Part {
        val requestBody = uriToRequestBody(context, uri)
        return MultipartBody.Part.createFormData(partName, "photo.jpg", requestBody)
    }
}