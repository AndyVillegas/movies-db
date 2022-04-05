package com.andy.movieapp.data.datasource.movie

import com.andy.movieapp.data.datasource.retrofit.RetrofitHelper
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRemoteDataSource(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private var moviesApi: MoviesApi = RetrofitHelper.retrofit.create(MoviesApiRetrofit::class.java)

    suspend fun find(id: Long): Movie {
        return withContext(dispatcher){
            moviesApi.find(id)
        }
    }

    suspend fun fetchPopularMovies(): PaginatedList<Movie> {
        return withContext(dispatcher){
            moviesApi.fetchPopularMovies()
        }
    }
}