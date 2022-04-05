package com.andy.movieapp.data.repository

import com.andy.movieapp.data.datasource.movie.MoviesRemoteDataSource
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList

class MoviesRepository {
    private val moviesRemoteDataSource: MoviesRemoteDataSource = MoviesRemoteDataSource()
    suspend fun fetchPopularMovies(): PaginatedList<Movie> {
        return moviesRemoteDataSource.fetchPopularMovies()
    }
    suspend fun find(id: Long): Movie {
        return moviesRemoteDataSource.find(id)
    }
}