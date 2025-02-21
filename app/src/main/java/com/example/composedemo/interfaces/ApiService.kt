package com.senses.composabletask.interfaces

import com.example.composedemo.model.City
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.senses.composabletask.model.Post

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("City")
    suspend fun getCity(): List<City>

    companion object {
        private const val BASE_URL = "https://caa123699ed2df1d1453.free.beeceptor.com"
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
