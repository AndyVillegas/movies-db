package com.andy.movieapp.data.datasource.movie

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList

interface MoviesApi {
    suspend fun find(id: Long): Movie
    suspend fun fetchPopularMovies(page: Int): PaginatedList<Movie>
}