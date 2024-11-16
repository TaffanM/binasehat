package com.mage.binasehat.repository

import com.google.gson.Gson
import com.mage.binasehat.data.remote.api.MlApiService
import com.mage.binasehat.data.remote.response.FoodScanResponse
import com.mage.binasehat.data.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject


class PredictFoodRepository @Inject constructor(
    private val mlApiService: MlApiService
) {

    fun predict(image: File): Flow<UiState<FoodScanResponse>> = flow {
        emit(UiState.Loading) // Emitting loading state initially

        // Preparing the file to be uploaded
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            "image", // "image" is the key expected by the server
            image.name,
            requestImageFile
        )

        try {
            // Making the network request
            val response = mlApiService.uploadImage(multipartBody)

            // If the response is successful, emit the success state with the data
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UiState.Success(it)) // Success state with the response body
                } ?: run {
                    emit(UiState.Error("Empty response body"))
                }
            } else {
                // In case of an unsuccessful response, emit an error with the response message
                emit(UiState.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: HttpException) {
            // Handling HTTP exceptions and emitting the error
            emit(UiState.Error("HTTP error: ${e.message()}"))
        } catch (e: IOException) {
            // Handling IO exceptions (network failure, etc.)
            emit(UiState.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            // Handling any other general exceptions
            emit(UiState.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
}