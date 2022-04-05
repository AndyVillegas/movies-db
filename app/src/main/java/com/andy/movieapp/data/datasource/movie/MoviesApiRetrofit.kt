package com.andy.movieapp.data.datasource.movie

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiRetrofit: MoviesApi{
    @GET("movie/popular")
    override suspend fun fetchPopularMovies(@Query("page") page: Int): PaginatedList<Movie>
    @GET("movie/{id}")
    override suspend fun find(@Path("id") id: Long): Movie
}