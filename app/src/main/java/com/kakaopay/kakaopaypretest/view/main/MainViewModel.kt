package com.kakaopay.kakaopaypretest.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.constant.LoadingState
import com.kakaopay.kakaopaypretest.model.MainRepository
import com.kakaopay.kakaopaypretest.model.SearchResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _imageSearchResultLiveData = MutableLiveData<SearchResult>().apply {
        value = SearchResult(mutableListOf(), null)
    }
    val imageSearchResultLiveData: LiveData<SearchResult> get() = _imageSearchResultLiveData

    private val _state = MutableLiveData<LoadingState>().apply {
        value = LoadingState.WAIT
    }
    val state: LiveData<LoadingState> get() = _state

    private var page: Int = 0
    private var query: String = ""
    private var isNextPage: Boolean = false

    private val repository: MainRepository by lazy {
        MainRepository()
    }

    private fun getSearchSingleImage(
            query: String,
            sort: KakaoImageSearchSortEnum,
            page: Int,
            size: Int
    ): Single<SearchResult> {
        return repository.searchImage(query, sort, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchImage(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int) {
        _state.value = LoadingState.LOADING
        this.query = query
        this.page = page
        addDisposable(
                getSearchSingleImage(this.query, sort, page, size)
                        .subscribe({
                            if (it.documents.size == 0) {
                                _state.value = LoadingState.NOT_FOUND
                            } else {
                                isNextPage = it.meta!!.is_end
                                _state.value = LoadingState.WAIT
                            }
                            _imageSearchResultLiveData.value = it
                        }, {
                            _state.value = LoadingState.NETWORK_ERROR
                        })
        )
    }

    fun addSearchImage(sort: KakaoImageSearchSortEnum, size: Int) {
        _state.value = LoadingState.LOADING
        if (!isNextPage) {
            addDisposable(
                    getSearchSingleImage(this.query, sort, page + 1, size)
                            .subscribe({
                                isNextPage = it.meta!!.is_end
                                _state.value = LoadingState.WAIT
                                val result = _imageSearchResultLiveData.value
                                result!!.documents.addAll(it.documents)
                                _imageSearchResultLiveData.value = result
                                page += 1
                            }, {
                                _state.value = LoadingState.NETWORK_ERROR
                            })
            )
        }
    }

    fun clearItem() {
        _imageSearchResultLiveData.postValue(SearchResult(mutableListOf(), null))
        page = 0
        query = ""
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}
