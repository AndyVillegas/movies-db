package com.andy.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.repository.MoviesRepository
import com.andy.movieapp.domain.FetchPopularMoviesUseCase
import com.andy.movieapp.shared.MessageType
import com.andy.movieapp.shared.MessageUI
import kotlinx.coroutines.launch
import java.io.IOException

class PopularMoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepository()
    private val fetchPopularMoviesUseCase = FetchPopularMoviesUseCase(moviesRepository)
    var isFiltered: Boolean = false
    private val _filteredMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val filteredMovies: LiveData<List<Movie>>
        get() = _filteredMovies
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies
    private val _message: MutableLiveData<MessageUI> = MutableLiveData()
    val message: LiveData<MessageUI>
        get() = _message
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var page: Int = 1
    private val _cachedMovies: HashMap<Int, List<Movie>> = HashMap()
    fun fetchMovies(){
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val movies = fetchPopularMoviesUseCase(page)
                _movies.postValue(movies)
                if(!_cachedMovies.containsKey(page)) _cachedMovies[page] = movies
            }catch (ioe: IOException){
                _message.postValue(MessageUI(MessageType.ERROR, "Error when try fetch movies"))
            }finally {
                _isLoading.postValue(false)
            }
        }
    }
    fun nextPage(){
        page += 1
        fetchMovies()
    }
    fun searchInMemory(search: String){
        isFiltered = search.isNotBlank()
        val movies = _cachedMovies.flatMap { it.value }
        if(isFiltered)
            _filteredMovies.postValue(
                movies.filter {
                    movie -> movie.title.uppercase().contains(search.uppercase())
                })
        else
            _filteredMovies.postValue(movies)
    }
}