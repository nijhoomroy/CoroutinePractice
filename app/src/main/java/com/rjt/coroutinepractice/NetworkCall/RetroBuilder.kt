package com.rjt.coroutinepractice.NetworkCall

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetroBuilder {

    const val BASE_URL = "https://open-api.xyz/"

    val retrofitbuilder : Retrofit.Builder by lazy{

        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: APIService by lazy {

        retrofitbuilder.build().create(APIService::class.java)
    }
}