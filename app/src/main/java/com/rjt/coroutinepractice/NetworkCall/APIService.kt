package com.rjt.coroutinepractice.NetworkCall

import com.rjt.coroutinepractice.Model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("placeholder/user/{userid}")

    suspend fun getUserAsync(
        @Path("userId") userId: String
    ) : UserModel
}