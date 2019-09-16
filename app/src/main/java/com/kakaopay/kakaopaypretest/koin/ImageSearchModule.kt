package com.kakaopay.kakaopaypretest.koin

import com.kakaopay.kakaopaypretest.coroutine.CoroutineRetrofit
import com.kakaopay.kakaopaypretest.coroutine.ImageSearchRepository
import com.kakaopay.kakaopaypretest.coroutine.ImageSearchRepositoryImpl
import org.koin.dsl.module


val searchModule = module {

    // Repository 생성자인 Retrofit 구현체 주입
    factory { CoroutineRetrofit.getSearchService() }

    // Usecase 생성자인 Repository 주입
    factory<ImageSearchRepository> { ImageSearchRepositoryImpl(remote = get()) }

}
