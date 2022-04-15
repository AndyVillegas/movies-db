package com.andy.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andy.movieapp.data.model.Movie
import com.andy.movieapp.domain.GetMovieUseCase
import com.andy.movieapp.shared.MessageType
import com.andy.movieapp.shared.MessageUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : BaseMessageViewModel() {
    private var _currentMovie: MutableLiveData<Movie> = MutableLiveData()
    val currentMovie: LiveData<Movie>
        get() = _currentMovie

    fun find(id: Long) {
        try {
            viewModelScope.launch {
                val movie = getMovieUseCase(id)
                _currentMovie.postValue(movie)
            }
        } catch (ioe: IOException) {
            _message.postValue(MessageUI(MessageType.ERROR, "Error when try get the movie"))
        }
    }
}