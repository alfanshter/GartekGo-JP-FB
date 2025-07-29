package com.ptpws.GartekGo.network

import com.ptpws.GartekGo.model.RegisterUserRequest
import com.ptpws.GartekGo.model.RegisterUserResponse
import com.ptpws.GartekGo.model.UpdateUserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST




interface UserApiService {
    @POST("/")
    suspend fun registerUser(
        @Body request: RegisterUserRequest
    ): Response<RegisterUserResponse>

    @POST("/")
    suspend fun updateUser(
        @Body request: UpdateUserRequest
    ): Response<RegisterUserResponse>

    @POST("/")
    suspend fun deleteUser(
        @Body request: UpdateUserRequest
    ): Response<RegisterUserResponse>
}