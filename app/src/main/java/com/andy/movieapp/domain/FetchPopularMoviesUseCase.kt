package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import com.andy.movieapp.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.HashMap
import javax.inject.Inject

class FetchPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    private val _moviesCached: HashMap<Int, PaginatedList<Movie>> = hashMapOf()
    val moviesCached
        get() = _moviesCached
    suspend operator fun invoke(page: Int = 1): List<Movie> = withContext(Dispatchers.Default){
        val paginatedMovies = _moviesCached.getOrPut(page) { moviesRepository.fetchPopularMovies(page) }
        paginatedMovies.results
    }
}