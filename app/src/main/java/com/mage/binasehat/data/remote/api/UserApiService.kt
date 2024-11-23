package com.mage.binasehat.data.remote.api

import com.mage.binasehat.data.remote.model.FormSubmissionRequest
import com.mage.binasehat.data.remote.model.LoginRequest
import com.mage.binasehat.data.remote.model.RegisterRequest
import com.mage.binasehat.data.remote.model.RunSubmissionRequest
import com.mage.binasehat.data.remote.response.DetailUserResponse
import com.mage.binasehat.data.remote.response.FormResponse
import com.mage.binasehat.data.remote.response.LoginResponse
import com.mage.binasehat.data.remote.response.RegisterResponse
import com.mage.binasehat.data.remote.response.RunningResponse
import com.mage.binasehat.data.remote.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.Date

interface UserApiService {

    @POST("/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST("/forms")
    suspend fun submitForm(
        @Header("Authorization") authToken: String,
        @Body formSubmissionRequest: FormSubmissionRequest
    ): FormResponse

    @POST("/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : LoginResponse

    @Multipart
    @POST("/upload")
    suspend fun upload(
        @Header("Authorization") authToken: String,
        @Part photo: MultipartBody.Part
    ): UploadResponse

    @GET("/user/detail")
    suspend fun getUserDetail(
        @Header("Authorization") authToken: String,
    ) : DetailUserResponse

    @POST("/run")
    suspend fun submitRun(
        @Header("Authorization") authToken: String,
        @Body runSubmissionRequest: RunSubmissionRequest
    ): RunningResponse


}