package com.kakaopay.kakaopaypretest.di

import com.kakaopay.core.domain.ImageSearchUseCase
import com.kakaopay.core.remote.ImageSearchRemoteDataSource
import com.kakaopay.core.repository.search.ImageSearchRepository
import com.kakaopay.core.repository.search.ImageSearchRepositoryImpl
import com.kakaopay.kakaopaypretest.common.APIService
import com.kakaopay.kakaopaypretest.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val searchImageModule = module {

    //retrofit
    single { (get() as APIService).create(ImageSearchRemoteDataSource::class.java) }

    //repository
    factory<ImageSearchRepository> { ImageSearchRepositoryImpl(remote = get()) }

    //usecase
    factory { ImageSearchUseCase(repository = get()) }

    //viewmodel
    viewModel {
        MainViewModel(imageSearch = get())

    }


}