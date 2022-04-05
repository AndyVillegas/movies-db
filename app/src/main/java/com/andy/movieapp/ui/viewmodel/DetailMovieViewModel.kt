package com.andy.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.data.repository.MoviesRepository
import com.andy.movieapp.domain.GetMovieUseCase
import com.andy.movieapp.shared.MessageType
import com.andy.movieapp.shared.MessageUI
import kotlinx.coroutines.launch
import java.io.IOException

class DetailMovieViewModel: ViewModel(){
    private val moviesRepository: MoviesRepository = MoviesRepository()
    private val getMovieUseCase = GetMovieUseCase(moviesRepository)
    private var _currentMovie: MutableLiveData<Movie> = MutableLiveData()
    val currentMovie: LiveData<Movie>
        get() = _currentMovie
    private val _message: MutableLiveData<MessageUI> = MutableLiveData()
    val message: LiveData<MessageUI>
        get() = _message
    fun find(id: Long){
        try {
            viewModelScope.launch {
                val movie = getMovieUseCase(id)
                _currentMovie.postValue(movie)
            }
        }catch (ioe: IOException){
            _message.postValue(MessageUI(MessageType.ERROR, "Error when try get the movie"))
        }
    }
}