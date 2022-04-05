package com.andy.movieapp.data.model

data class PaginatedList<T>(
    val page: Int,
    val results: List<T>,
    val totalResults: Int,
    val totalPages: Int
)