package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import com.andy.movieapp.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.HashMap

class FetchPopularMoviesUseCase(
    private val moviesRepository: MoviesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val moviesCached: HashMap<Int, PaginatedList<Movie>> = hashMapOf()
    suspend operator fun invoke(page: Int = 1): List<Movie> = withContext(defaultDispatcher){
        val paginatedMovies = moviesCached.getOrPut(page) { moviesRepository.fetchPopularMovies(page) }
        paginatedMovies.results
    }
}