package com.kakaopay.kakaopaypretest.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaopay.kakaopaypretest.constant.LoadingState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class DetailViewModel : ViewModel() {


    private val _imageURL = MutableLiveData<String>().apply {
        value = ""
    }
    val imageURL: LiveData<String> get() = _imageURL

    private val _state = MutableLiveData<LoadingState>().apply {
        value = LoadingState.LOADING
    }
    val state: LiveData<LoadingState> get() = _state


    fun setLiveDataImageURL(url: String) {
        _imageURL.postValue(url)
    }

    fun setStateProgressEnd() {
        _state.value = LoadingState.WAIT
    }

    private val compositeDisposable by lazy {
        CompositeDisposable()

    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}

