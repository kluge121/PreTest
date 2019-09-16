package com.kakaopay.kakaopaypretest.common

import com.kakaopay.kakaopaypretest.constant.KAKAO_API_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(KAKAO_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun getRetrofitInstance(): Retrofit = retrofit

    }


}
