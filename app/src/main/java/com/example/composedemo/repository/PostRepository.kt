package com.senses.composabletask.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.senses.composabletask.model.Post
import com.senses.composabletask.interfaces.ApiService


class PostRepository(private val apiService: ApiService) {

    suspend fun getPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            apiService.getPosts()
        }
    }
}
