package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    private val cachedMovies = hashMapOf<Long, Movie>()
    suspend operator fun invoke(id: Long): Movie = withContext(Dispatchers.Default) {
        val movie = cachedMovies.getOrPut(id) { moviesRepository.find(id) }
        movie
    }
}