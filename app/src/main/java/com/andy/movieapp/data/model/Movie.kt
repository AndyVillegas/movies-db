package com.andy.movieapp.data.model

import com.andy.movieapp.shared.Constants

data class Movie (
    val id: Long,
    val posterPath: String,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val genreIDS: List<Long>,
    val genres: List<Genre>,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String,
    val popularity: Double,
    val voteCount: Long,
    val video: Boolean,
    val voteAverage: Double
){
    val fullPosterPath: String
    get() = Constants.URL_IMAGE_BASE_500 + this.posterPath
}
