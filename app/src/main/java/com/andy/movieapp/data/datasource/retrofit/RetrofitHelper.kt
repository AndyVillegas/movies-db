package com.andy.movieapp.data.datasource.retrofit

import com.andy.movieapp.data.datasource.retrofit.interceptors.MovieDBInterceptor
import com.andy.movieapp.shared.Constants
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val retrofit: Retrofit
    get() {
        val movieDBInterceptor = MovieDBInterceptor()
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(movieDBInterceptor)
            .build()
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }
}