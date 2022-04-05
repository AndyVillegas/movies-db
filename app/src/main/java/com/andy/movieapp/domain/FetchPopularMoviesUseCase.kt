package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchPopularMoviesUseCase(
    private val moviesRepository: MoviesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(page: Int = 1): List<Movie> = withContext(defaultDispatcher){
        val paginatedMovies = moviesRepository.fetchPopularMovies(page)
        paginatedMovies.results
    }
}