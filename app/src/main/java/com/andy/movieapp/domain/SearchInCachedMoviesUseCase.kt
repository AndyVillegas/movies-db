package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import java.util.HashMap
import javax.inject.Inject

class SearchInCachedMoviesUseCase @Inject constructor() {
    operator fun invoke(
        moviesCached: HashMap<Int, PaginatedList<Movie>>,
        search: String
    ): List<Movie> {
        val movies = moviesCached.flatMap { it.value.results }
        return if (search.isNotBlank())
            movies.filter { movie ->
                movie.title.uppercase().contains(search.uppercase())
            }
        else
            movies
    }
}