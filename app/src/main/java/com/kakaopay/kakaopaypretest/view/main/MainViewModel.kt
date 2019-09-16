package com.kakaopay.kakaopaypretest.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaopay.core.domain.ImageSearchUseCase
import com.kakaopay.core.entity.ImageSearchEntity
import com.kakaopay.core.model.search.KakaoImageSearchSortEnum
import com.kakaopay.kakaopaypretest.constant.LoadingState
import com.kakaopay.kakaopaypretest.model.MainRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
    private val imageSearch: ImageSearchUseCase
) : ViewModel() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _imageSearchResultLiveData = MutableLiveData<ImageSearchEntity>().apply {
        value = ImageSearchEntity(mutableListOf(), null)
    }
    val imageResultSearchEntityLiveData: LiveData<ImageSearchEntity> get() = _imageSearchResultLiveData


    private val _state = MutableLiveData<LoadingState>().apply {
        value = LoadingState.WAIT
    }
    val state: LiveData<LoadingState> get() = _state


    private var page: Int = 0
    private var query: String = ""
    private var isEndPage: Boolean = false

    private val repository: MainRepository by lazy {
        MainRepository()
    }


    fun getSearchImageResult(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            _state.postValue(LoadingState.LOADING)
            _imageSearchResultLiveData.postValue(imageSearch(query, sort, page, size))
            _state.postValue(LoadingState.SUCCESS)
        }

    }

    private fun getSearchSingleImage(
        query: String,
        sort: KakaoImageSearchSortEnum,
        page: Int,
        size: Int
    ): Single<ImageSearchEntity> {
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
                        isEndPage = it.meta!!.is_end
                        _state.value = LoadingState.SUCCESS
                    }
                    _imageSearchResultLiveData.value = it
                }, {
                    _state.value = LoadingState.NETWORK_ERROR
                })
        )
    }

    fun addSearchImage(sort: KakaoImageSearchSortEnum, size: Int) {
        _state.value = LoadingState.LOADING
        if (!isEndPage) {
            addDisposable(
                getSearchSingleImage(this.query, sort, page + 1, size)
                    .subscribe({
                        isEndPage = it.meta!!.is_end
                        _state.value = LoadingState.SUCCESS
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
        _imageSearchResultLiveData.postValue(
            ImageSearchEntity(
                mutableListOf(),
                null
            )
        )
        page = 0
        query = ""
        _state.value = LoadingState.WAIT
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}
