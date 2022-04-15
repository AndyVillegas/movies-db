package com.andy.movieapp.data.datasource.movie

import com.andy.movieapp.data.datasource.retrofit.RetrofitHelper
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor() {
    private var moviesApi: MoviesApi = RetrofitHelper.retrofit.create(MoviesApiRetrofit::class.java)

    suspend fun find(id: Long): Movie {
        return withContext(Dispatchers.IO){
            moviesApi.find(id)
        }
    }

    suspend fun fetchPopularMovies(page: Int): PaginatedList<Movie> {
        return withContext(Dispatchers.IO){
            moviesApi.fetchPopularMovies(page)
        }
    }
}