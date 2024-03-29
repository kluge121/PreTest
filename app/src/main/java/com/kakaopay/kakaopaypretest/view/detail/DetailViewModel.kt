package com.kakaopay.kakaopaypretest.view.detail

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakaopay.kakaopaypretest.R
import com.kakaopay.kakaopaypretest.constant.LoadingState
import com.kakaopay.kakaopaypretest.util.BitmapSaver
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val compositeDisposable by lazy {
        CompositeDisposable()

    }


    private val _imageURL = MutableLiveData<String>().apply {
        value = ""
    }
    val imageURL: LiveData<String> get() = _imageURL

    private val _state = MutableLiveData<LoadingState>().apply {
        value = LoadingState.LOADING
    }
    val state: LiveData<LoadingState> get() = _state

    private val _imageBitmap = MutableLiveData<Bitmap?>().apply {
        value = null
    }
    val imageBitmap: LiveData<Bitmap?> get() = _imageBitmap


    fun setLiveDataImageURL(url: String) {
        _imageURL.postValue(url)
    }

    fun setStateWait() {
        _state.value = LoadingState.WAIT
    }

    fun setStateNotExit() {
        _state.value = LoadingState.NOT_EXIST
    }

    fun setBitmap(bitmap: Bitmap) {
        _imageBitmap.value = bitmap
    }


    fun saveImage(): Boolean {
        if (imageBitmap.value != null && state.value != LoadingState.LOADING) {
            _state.value = LoadingState.LOADING
            val observable = Maybe.just(imageBitmap.value)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
            addDisposable(
                observable
                    .subscribe({
                        BitmapSaver.saveImage(it!!, appContext, appContext.resources.getString(R.string.app_name))
                        _state.postValue(LoadingState.SUCCESS)
                    }, {
                        _state.postValue(LoadingState.NETWORK_ERROR)
                    })
            )
            return true
        } else if (imageBitmap.value == null) {
            _state.value = LoadingState.NOT_EXIST
            return false
        }
        return false
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}



