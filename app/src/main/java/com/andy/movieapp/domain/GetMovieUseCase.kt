package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val cachedMovies = hashMapOf<Long, Movie>()
    suspend operator fun invoke(id: Long): Movie = withContext(defaultDispatcher){
        val movie = cachedMovies.getOrPut(id) { moviesRepository.find(id) }
        movie
    }
}