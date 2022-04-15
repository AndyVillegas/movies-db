package com.andy.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andy.movieapp.shared.MessageUI

open class BaseMessageViewModel : ViewModel() {
    protected val _message: MutableLiveData<MessageUI> = MutableLiveData()
    val message: LiveData<MessageUI>
        get() = _message
}