package com.andy.movieapp.data.repository

import com.andy.movieapp.data.datasource.movie.MoviesRemoteDataSource
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
){
    suspend fun fetchPopularMovies(page: Int): PaginatedList<Movie> {
        return moviesRemoteDataSource.fetchPopularMovies(page)
    }
    suspend fun find(id: Long): Movie {
        return moviesRemoteDataSource.find(id)
    }
}