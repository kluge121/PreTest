package com.kakaopay.kakaopaypretest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

    private val _imageSearchResultLiveData = MutableLiveData<SearchResult>().apply {
        value = SearchResult(mutableListOf(), null)
    }
    val imageSearchResultLiveData: LiveData<SearchResult> get() = _imageSearchResultLiveData

    private val _state = MutableLiveData<LoadingState>().apply {
        value = LoadingState.WAIT
    }
    val state: LiveData<LoadingState> get() = _state


    private val repository: MainRepository by lazy {
        MainRepository()
    }

    fun searchImage(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int) {
        _state.value = LoadingState.LOADING
        addDisposable(
                repository.searchImage(query, sort, page, size)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (it.documents.size == 0) {
                                _state.value = LoadingState.NOT_FOUND
                            } else {
                                _state.value = LoadingState.WAIT
                            }
                            _imageSearchResultLiveData.value = it
                        }, {
                            Log.e("dddd",it.toString())
                            _state.value = LoadingState.NETWORK_ERROR
                        })
        )
    }

    fun clearItem() {
        _imageSearchResultLiveData.postValue(SearchResult(mutableListOf(), null))
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
