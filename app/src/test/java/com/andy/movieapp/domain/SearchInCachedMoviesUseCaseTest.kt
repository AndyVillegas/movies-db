package com.andy.movieapp.domain

import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.model.PaginatedList
import com.andy.movieapp.data.repository.MoviesRepository
import com.andy.movieapp.fixtures.moviesFixture
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchInCachedMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var searchInCachedMoviesUseCase: SearchInCachedMoviesUseCase
    private lateinit var fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
    private val paginatedList = PaginatedList(
        page = 1,
        results = moviesFixture,
        totalResults = moviesFixture.size,
        totalPages = 1
    )

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        searchInCachedMoviesUseCase = SearchInCachedMoviesUseCase()
        fetchPopularMoviesUseCase = FetchPopularMoviesUseCase(moviesRepository)
    }

    @Test
    fun `when  the  search  text  not  match  with  any  movie  should  return  an  empty  list`() = runBlocking {
        //Given
        coEvery { moviesRepository.fetchPopularMovies(1) } returns paginatedList
        //When
        fetchPopularMoviesUseCase(1)
        val movies = searchInCachedMoviesUseCase(
            fetchPopularMoviesUseCase.moviesCached,
            "Any Movie"
        )
        //Then
        assert(movies.isEmpty())
    }

    @Test
    fun `when  the  search  text  match  with  any  movie  should  return  an  filled  list`() = runBlocking {
        //Given
        coEvery { moviesRepository.fetchPopularMovies(1) } returns paginatedList
        //When
        fetchPopularMoviesUseCase(1)
        val movies = searchInCachedMoviesUseCase(
            fetchPopularMoviesUseCase.moviesCached,
            "My First Movie"
        )
        //Then
        assert(movies.isNotEmpty())
    }

    @Test
    fun `when  the  search  text  is empty  should  return all movies`() = runBlocking {
        //Given
        coEvery { moviesRepository.fetchPopularMovies(1) } returns paginatedList
        //When
        fetchPopularMoviesUseCase(1)
        val movies = searchInCachedMoviesUseCase(
            fetchPopularMoviesUseCase.moviesCached,
            ""
        )
        //Then
        assert(movies.size == moviesFixture.size)
    }
}