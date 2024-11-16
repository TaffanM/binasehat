package com.mage.binasehat.data.remote.api

import com.mage.binasehat.data.remote.response.FoodScanResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MlApiService {

    @Multipart
    @POST("/predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<FoodScanResponse>

}