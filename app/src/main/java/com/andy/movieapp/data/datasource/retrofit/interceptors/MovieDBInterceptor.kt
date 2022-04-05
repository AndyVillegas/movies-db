package com.andy.movieapp.data.datasource.retrofit.interceptors

import com.andy.movieapp.shared.Constants
import okhttp3.Interceptor
import okhttp3.Response

class MovieDBInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", Constants.API_MOVIE_KEY)
            .addQueryParameter("language", Constants.LANGUAGE)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}