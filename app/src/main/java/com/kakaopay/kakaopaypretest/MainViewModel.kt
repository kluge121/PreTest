package com.kakaopay.kakaopaypretest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class MainViewModel : ViewModel() {

    private val _imageSearchResultLiveData = MutableLiveData<SearchResult>()

    val imageSearchResultLiveData: LiveData<SearchResult>
        get() = _imageSearchResultLiveData

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}
