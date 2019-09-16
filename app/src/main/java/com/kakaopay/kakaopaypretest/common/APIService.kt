package com.kakaopay.kakaopaypretest.common

import retrofit2.Retrofit


class APIService {

    private var retrofit : Retrofit = RetrofitProvider.getRetrofitInstance()


    fun <T> create(clazz: Class<T>) : T {
        return retrofit.create(clazz)
    }

}