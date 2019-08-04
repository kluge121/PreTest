package com.kakaopay.kakaopaypretest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaopay.kakaopaypretest.constant.KakaoImageSearchSortEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {


    private val repository: MainRepository by lazy {
        MainRepository()
    }

    var imageSearchResultLiveData = MutableLiveData<SearchResult>()


    fun searchImage(query: String, sort: KakaoImageSearchSortEnum, page: Int, size: Int) {
        addDisposable(
                repository.searchImage(query, sort, page, size)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer {
                            imageSearchResultLiveData.postValue(it)
                        })
        )
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
